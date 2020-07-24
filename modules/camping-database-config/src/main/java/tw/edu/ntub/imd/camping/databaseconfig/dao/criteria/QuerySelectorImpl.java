package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria;

import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.exception.NotSingleResultException;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.exception.WhereRestrictionExistException;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.order.OrderSupplier;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.EmptyResultChecker;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.RestrictionUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.WhereRestriction;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.function.CriteriaFunction;
import tw.edu.ntub.imd.camping.databaseconfig.dto.Pager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QuerySelectorImpl<E, R> implements QuerySelector<E, R> {
    private EntityManager entityManager;
    private CriteriaBuilder builder;
    private CriteriaQuery<R> query;
    private final List<Selection<?>> selectionList = new ArrayList<>();
    private Root<E> root;
    private final PredicateList predicateList = new PredicateList();
    private final List<Order> orderList = new ArrayList<>();
    private final List<Expression<?>> groupByList = new ArrayList<>();
    private final PredicateList havingList = new PredicateList();
    private boolean emptyResult;
    private boolean distinct;

    protected QuerySelectorImpl() {

    }

    public static <E> QuerySelectorImpl<E, E> create(EntityManager entityManager, Class<E> entityClass) {
        QuerySelectorImpl<E, E> querySelector = create(entityManager, entityClass, entityClass);
        querySelector.query.select(querySelector.root);
        return querySelector;
    }

    public static <E, R> QuerySelectorImpl<E, R> create(EntityManager entityManager, Class<E> entityClass, Class<R> returnClass) {
        QuerySelectorImpl<E, R> querySelector = new QuerySelectorImpl<>();
        querySelector.entityManager = entityManager;
        querySelector.builder = entityManager.getCriteriaBuilder();
        querySelector.query = querySelector.builder.createQuery(returnClass);
        querySelector.root = querySelector.query.from(entityClass);
        return querySelector;
    }

    public static <E, R> QuerySelectorImpl<E, R> create(EntityManager entityManager, Class<E> entityClass, SingularAttribute<E, R> selection) {
        QuerySelectorImpl<E, R> querySelector = create(entityManager, entityClass, selection.getJavaType());
        querySelector.query.select(querySelector.root.get(selection));
        return querySelector;
    }

    @Override
    @SafeVarargs
    public final QuerySelectorImpl<E, R> addSelection(@Nonnull SingularAttribute<? super E, ?>... attributes) {
        for (SingularAttribute<? super E, ?> attribute : attributes) {
            selectionList.add(root.get(attribute));
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final QuerySelectorImpl<E, R> addSelection(@Nonnull CriteriaFunction<E, ?>... functions) {
        for (CriteriaFunction<E, ?> function : functions) {
            selectionList.add(function.getExpression(builder, root));
        }
        return this;
    }

    @Override
    public QuerySelectorImpl<E, R> distinct() {
        distinct = true;
        return this;
    }

    public QuerySelectorImpl<E, R> setWhereRestriction(@Nonnull SingularAttribute<E, Boolean> attribute) {
        setWhereRestriction(root.get(attribute));
        return this;
    }

    protected void setWhereRestriction(@Nonnull Expression<Boolean> booleanExpression) {
        if (predicateList.isEmpty()) {
            query.where(booleanExpression);
        } else {
            throw new WhereRestrictionExistException();
        }
    }

    public <V> QuerySelectorImpl<E, R> add(@Nonnull SingularAttribute<E, V> attribute, V value) {
        return add(RestrictionUtils.equal(attribute, value));
    }

    @Override
    public QuerySelectorImpl<E, R> add(@Nonnull WhereRestriction<E> predicateSupplier) {
        if (predicateSupplier instanceof EmptyResultChecker && ((EmptyResultChecker) predicateSupplier).isEmpty()) {
            emptyResult = true;
        } else {
            setWhereRestriction(predicateSupplier.get(builder, root));
        }
        return this;
    }

    protected void setWhereRestriction(@Nullable Predicate predicate) {
        if (predicate != null) {
            predicateList.add(predicate);
        }
    }

    @Override
    public <J> JoinQuerySelector<E, J> join(@Nonnull SingularAttribute<E, J> joinAttribute, @Nonnull JoinType joinType) {
        return new JoinQuerySelector<>(this, joinAttribute, joinType);
    }

    @Override
    public <V> QuerySelectorImpl<E, R> orderBy(@Nonnull SingularAttribute<E, V> attribute, @Nonnull OrderSupplier<V> orderSupplier) {
        return orderBy(orderSupplier.get(builder, root.get(attribute)));
    }

    @Override
    public QuerySelectorImpl<E, R> orderBy(@Nonnull SingularAttribute<? super E, ?> attribute, @Nonnull OrderType orderType) {
        orderBy(orderType == OrderType.ASC ? builder.asc(root.get(attribute)) : builder.desc(root.get(attribute)));
        return this;
    }

    public QuerySelectorImpl<E, R> orderBy(Order order) {
        orderList.add(order);
        return this;
    }

    @Override
    @SafeVarargs
    public final QuerySelectorImpl<E, R> groupBy(@Nonnull SingularAttribute<? super E, ?>... attributes) {
        for (SingularAttribute<? super E, ?> attribute : attributes) {
            groupByList.add(root.get(attribute));
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final QuerySelectorImpl<E, R> groupBy(@Nonnull CriteriaFunction<E, ?>... functions) {
        for (CriteriaFunction<E, ?> function : functions) {
            groupByList.add(function.getExpression(builder, root));
        }
        return this;
    }

    @Override
    public QuerySelectorImpl<E, R> having(@Nonnull WhereRestriction<E> predicateSupplier) {
        if (predicateSupplier instanceof EmptyResultChecker && ((EmptyResultChecker) predicateSupplier).isEmpty()) {
            emptyResult = true;
        } else {
            Predicate having = predicateSupplier.get(builder, root);
            if (having != null) {
                havingList.add(having);
            }
        }
        return this;
    }

    @Nonnull
    @Override
    public List<R> getResultList(Pager pager) {
        if (emptyResult) {
            return Collections.emptyList();
        } else {
            if (pager.isInfinity()) {
                return createTypedQuery().getResultList();
            } else {
                return createTypedQuery()
                        .setFirstResult(pager.getFirstResultIndex())
                        .setMaxResults(pager.getCount())
                        .getResultList();
            }
        }
    }

    private TypedQuery<R> createTypedQuery() {
        setQuery();
        if (!selectionList.isEmpty()) {
            query.multiselect(selectionList);
        }
        if (!orderList.isEmpty()) {
            query.orderBy(orderList);
        }
        return entityManager.createQuery(query);
    }

    @Nonnull
    @Override
    public List<R> getResultList() {
        return emptyResult ? Collections.emptyList() : createTypedQuery().getResultList();
    }

    private void setQuery() {
        if (predicateList.isNotEmpty()) {
            query.where(predicateList.get());
        }
        if (!groupByList.isEmpty()) {
            query.groupBy(groupByList);
        }
        if (havingList.isNotEmpty()) {
            query.having(havingList.get());
        }
        query.distinct(distinct);
    }

    @Override
    public Optional<R> getSingleResult() {
        setQuery();
        try {
            return emptyResult ? Optional.empty() : Optional.of(entityManager.createQuery(query).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NonUniqueResultException e) {
            throw new NotSingleResultException(query.getResultType());
        }
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected CriteriaBuilder getBuilder() {
        return builder;
    }

    protected CriteriaQuery<R> getQuery() {
        return query;
    }

    protected Root<E> getRoot() {
        return root;
    }

    protected List<Selection<?>> getSelectionList() {
        return selectionList;
    }

    protected PredicateList getPredicateList() {
        return predicateList;
    }

    protected List<Order> getOrderList() {
        return orderList;
    }

    protected List<Expression<?>> getGroupByList() {
        return groupByList;
    }

    protected PredicateList getHavingList() {
        return havingList;
    }

    protected boolean isEmptyResult() {
        return emptyResult;
    }

    protected void setEmptyResult(boolean emptyResult) {
        if (!this.emptyResult) {
            this.emptyResult = emptyResult;
        }
    }
}
