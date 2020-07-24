package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public class NotEqualRestriction<E, V> extends AbstractRestriction<E, V> {
    private final V value;
    private boolean ignoreCase;

    public NotEqualRestriction(@Nonnull ExpressionSupplier<E, V> supplier, @Nullable V value) {
        super(supplier);
        this.value = value;
    }

    public void ignoreCase() {
        ignoreCase = true;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Predicate get(@Nonnull CriteriaBuilder builder, @Nonnull Expression<V> expression) {
        if (value != null) {
            if (ignoreCase && value instanceof String) {
                return builder.notEqual(builder.lower((Expression<String>) expression), ((String) value).toLowerCase());
            } else {
                return builder.notEqual(expression, value);
            }
        } else {
            return builder.isNotNull(expression);
        }
    }
}
