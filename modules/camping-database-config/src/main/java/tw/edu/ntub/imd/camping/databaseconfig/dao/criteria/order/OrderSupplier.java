package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

public interface OrderSupplier<V> {
    Order get(CriteriaBuilder builder, Expression<V> expression);
}
