package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public class EqualRestriction<E, V> extends AbstractRestriction<E, V> {
    private final V value;
    private boolean isIgnoreCase;

    public EqualRestriction(@Nonnull ExpressionSupplier<E, V> supplier, @Nullable V value) {
        super(supplier);
        this.value = value;
    }

    public void ignoreCase() {
        this.isIgnoreCase = true;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Predicate get(@Nonnull CriteriaBuilder builder, @Nonnull Expression<V> expression) {
        if (value != null) {
            if (isIgnoreCase && value instanceof String) {
                return builder.equal(builder.lower((Expression<String>) expression), ((String) value).toLowerCase());
            } else {
                return builder.equal(expression, value);
            }
        } else {
            return builder.isNull(expression);
        }
    }
}
