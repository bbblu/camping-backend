package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria;

import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.function.CriteriaFunction;

import javax.annotation.Nonnull;
import javax.persistence.metamodel.SingularAttribute;

public interface QuerySelectionHandler<E> {
    QuerySelectionHandler<E> addSelection(@Nonnull SingularAttribute<? super E, ?>... attributes);

    QuerySelectionHandler<E> addSelection(@Nonnull CriteriaFunction<E, ?>... functions);

    QuerySelectionHandler<E> distinct();
}
