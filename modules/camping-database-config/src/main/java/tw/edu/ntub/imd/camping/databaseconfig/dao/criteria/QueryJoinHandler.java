package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria;

import javax.annotation.Nonnull;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.SingularAttribute;

public interface QueryJoinHandler<E> {
    <J> JoinQuerySelector<E, J> join(@Nonnull SingularAttribute<E, J> joinAttribute, @Nonnull JoinType joinType);

    default <J> JoinQuerySelector<E, J> join(@Nonnull SingularAttribute<E, J> joinAttribute) {
        return innerJoin(joinAttribute);
    }

    default <J> JoinQuerySelector<E, J> innerJoin(@Nonnull SingularAttribute<E, J> joinAttribute) {
        return join(joinAttribute);
    }

    default <J> JoinQuerySelector<E, J> leftJoin(@Nonnull SingularAttribute<E, J> joinAttribute) {
        return join(joinAttribute, JoinType.LEFT);
    }
}
