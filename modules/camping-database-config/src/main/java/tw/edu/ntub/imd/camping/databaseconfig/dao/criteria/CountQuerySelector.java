package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria;

import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.WhereRestriction;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.SingularAttribute;

public class CountQuerySelector<E> implements QueryRestrictionHandler<E>, QueryJoinHandler<E> {
    private final QuerySelectorImpl<E, Long> querySelector;

    public CountQuerySelector(QuerySelectorImpl<E, Long> querySelector) {
        this.querySelector = querySelector;
    }

    @Override
    public final CountQuerySelector<E> add(@Nonnull WhereRestriction<E> predicateSupplier) {
        querySelector.add(predicateSupplier);
        return this;
    }

    @Override
    public <J> JoinQuerySelector<E, J> join(@Nonnull SingularAttribute<E, J> joinAttribute, @Nonnull JoinType joinType) {
        return querySelector.join(joinAttribute, joinType);
    }

    @SafeVarargs
    public final CountQuerySelector<E> groupBy(SingularAttribute<? super E, ?>... attributes) {
        querySelector.groupBy(attributes);
        return this;
    }

    public CountQuerySelector<E> having(WhereRestriction<E> predicateSupplier) {
        querySelector.having(predicateSupplier);
        return this;
    }

    public Long getResult() {
        if (querySelector.isEmptyResult()) {
            return 0L;
        } else {
            EntityManager entityManager = querySelector.getEntityManager();
            CriteriaBuilder builder = querySelector.getBuilder();
            CriteriaQuery<Long> query = querySelector.getQuery();
            PredicateList predicateList = querySelector.getPredicateList();
            query.select(builder.count(querySelector.getRoot()));
            if (predicateList.isNotEmpty()) {
                query.where(predicateList.get());
            }
            return entityManager.createQuery(query).getSingleResult();
        }
    }
}
