package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Collection;

public class InRestriction<E, V> extends AbstractRestriction<E, V> implements EmptyResultChecker {
    private final Object[] value;
    private boolean empty;

    public InRestriction(@Nonnull ExpressionSupplier<E, V> supplier, @Nullable Collection<V> value) {
        super(supplier);
        this.value = value != null ? value.toArray() : null;
    }

    @SafeVarargs
    public InRestriction(@Nonnull ExpressionSupplier<E, V> supplier, @Nullable V... value) {
        super(supplier);
        this.value = value;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    @Override
    protected Predicate get(@Nonnull CriteriaBuilder builder, @Nonnull Expression<V> expression) {
        if (value != null && value.length != 0) {
            return expression.in(value);
        } else {
            empty = true;
            return null;
        }
    }
}
