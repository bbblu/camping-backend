package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria;

import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.order.OrderSupplier;

import javax.annotation.Nonnull;
import javax.persistence.metamodel.SingularAttribute;

public interface QueryOrderHandler<E> {
    QueryOrderHandler<E> orderBy(@Nonnull SingularAttribute<? super E, ?> attribute, @Nonnull OrderType orderType);

    <V> QueryOrderHandler<E> orderBy(@Nonnull SingularAttribute<E, V> expressionSupplier, @Nonnull OrderSupplier<V> orderSupplier);

    default QueryOrderHandler<E> orderBy(@Nonnull SingularAttribute<E, ?> attribute) {
        return orderBy(attribute, OrderType.ASC);
    }
}
