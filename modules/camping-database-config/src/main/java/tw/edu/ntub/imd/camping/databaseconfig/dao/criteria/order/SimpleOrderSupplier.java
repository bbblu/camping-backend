package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.order;

import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.OrderType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

public class SimpleOrderSupplier<V> implements OrderSupplier<V> {
    private final OrderType orderType;

    public SimpleOrderSupplier(OrderType orderType) {
        this.orderType = orderType;
    }

    public static <V> SimpleOrderSupplier<V> asc() {
        return new SimpleOrderSupplier<>(OrderType.ASC);
    }

    public static <V> SimpleOrderSupplier<V> desc() {
        return new SimpleOrderSupplier<>(OrderType.DESC);
    }

    @Override
    public Order get(CriteriaBuilder builder, Expression<V> expression) {
        return orderType == OrderType.ASC ? builder.asc(expression) : builder.desc(expression);
    }
}
