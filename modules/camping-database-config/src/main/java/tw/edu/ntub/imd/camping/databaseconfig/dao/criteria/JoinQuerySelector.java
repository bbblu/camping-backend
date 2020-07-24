package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria;

import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.exception.WhereRestrictionExistException;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.order.OrderSupplier;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.EmptyResultChecker;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.WhereRestriction;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.function.CriteriaFunction;

import javax.annotation.Nonnull;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

public class JoinQuerySelector<S, J> implements QueryRestrictionHandler<J>, QueryJoinHandler<J>, QueryOrderHandler<J> {
    private QuerySelectorImpl<S, ?> querySelector;
    private CriteriaBuilder builder;
    private CriteriaQuery<?> query;
    private Join<S, J> join;
    private List<Selection<?>> selectionList;
    private PredicateList predicateList;
    private List<Order> orderList;
    private List<Expression<?>> groupByList;
    private PredicateList havingList;

    protected JoinQuerySelector(QuerySelectorImpl<S, ?> querySelector, SingularAttribute<S, J> joinAttribute, JoinType joinType) {
        this.querySelector = querySelector;
        this.builder = querySelector.getBuilder();
        this.query = querySelector.getQuery();
        Root<S> from = querySelector.getRoot();
        this.join = from.join(joinAttribute, joinType);
        this.selectionList = querySelector.getSelectionList();
        this.predicateList = querySelector.getPredicateList();
        this.orderList = querySelector.getOrderList();
        this.groupByList = querySelector.getGroupByList();
        this.havingList = querySelector.getHavingList();
    }

    private JoinQuerySelector() {

    }

    public JoinQuerySelector<S, J> setWhereRestriction(@Nonnull SingularAttribute<J, Boolean> attribute) {
        if (predicateList.isEmpty()) {
            query.where(join.get(attribute));
        } else {
            throw new WhereRestrictionExistException();
        }
        return this;
    }

    @Override
    public JoinQuerySelector<S, J> add(@Nonnull WhereRestriction<J> predicateSupplier) {
        if (predicateSupplier instanceof EmptyResultChecker && ((EmptyResultChecker) predicateSupplier).isEmpty()) {
            querySelector.setEmptyResult(true);
        } else {
            Predicate predicate = predicateSupplier.get(builder, join);
            if (predicate != null) {
                predicateList.add(predicate);
            }
        }
        return this;
    }

    @SafeVarargs
    public final JoinQuerySelector<S, J> addSelection(@Nonnull SingularAttribute<J, ?>... attributes) {
        for (SingularAttribute<J, ?> attribute : attributes) {
            selectionList.add(join.get(attribute));
        }
        return this;
    }

    @SafeVarargs
    public final JoinQuerySelector<S, J> addSelection(@Nonnull CriteriaFunction<J, ?>... functions) {
        for (CriteriaFunction<J, ?> function : functions) {
            selectionList.add(function.getExpression(builder, join));
        }
        return this;
    }

    @Override
    public <T> JoinQuerySelector<J, T> join(@Nonnull SingularAttribute<J, T> joinAttribute, @Nonnull JoinType joinType) {
        JoinQuerySelector<J, T> result = new JoinQuerySelector<>();
        result.builder = builder;
        result.query = query;
        result.join = join.join(joinAttribute, joinType);
        result.predicateList = predicateList;
        result.orderList = orderList;
        result.groupByList = groupByList;
        return result;
    }

    @Override
    public JoinQuerySelector<S, J> orderBy(@Nonnull SingularAttribute<? super J, ?> attribute, @Nonnull OrderType orderType) {
        return orderBy(orderType == OrderType.ASC ? builder.asc(join.get(attribute)) : builder.desc(join.get(attribute)));
    }

    @Override
    public <V> JoinQuerySelector<S, J> orderBy(@Nonnull SingularAttribute<J, V> expressionSupplier, @Nonnull OrderSupplier<V> orderSupplier) {
        return orderBy(orderSupplier.get(builder, join.get(expressionSupplier)));
    }

    public JoinQuerySelector<S, J> orderBy(Order order) {
        orderList.add(order);
        return this;
    }

    @SafeVarargs
    public final JoinQuerySelector<S, J> groupBy(SingularAttribute<? super J, ?>... attributes) {
        for (SingularAttribute<? super J, ?> attribute : attributes) {
            groupByList.add(join.get(attribute));
        }
        return this;
    }

    public JoinQuerySelector<S, J> having(WhereRestriction<J> predicateSupplier) {
        if (predicateSupplier instanceof EmptyResultChecker && ((EmptyResultChecker) predicateSupplier).isEmpty()) {
            querySelector.setEmptyResult(true);
        } else {
            Predicate predicate = predicateSupplier.get(builder, join);
            if (predicate != null) {
                havingList.add(predicate);
            }
        }
        return this;
    }
}

