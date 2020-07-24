package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria;

import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.WhereRestriction;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.function.CriteriaFunction;

import javax.annotation.Nonnull;
import javax.persistence.metamodel.SingularAttribute;

public interface QueryGroupHandler<E> {
    QueryGroupHandler<E> groupBy(@Nonnull SingularAttribute<? super E, ?>... attributes);

    QueryGroupHandler<E> groupBy(@Nonnull CriteriaFunction<E, ?>... functions);

    QueryGroupHandler<E> having(@Nonnull WhereRestriction<E> predicateSupplier);
}
