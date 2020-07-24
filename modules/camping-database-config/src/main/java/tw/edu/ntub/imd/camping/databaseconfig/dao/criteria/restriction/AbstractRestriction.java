package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public abstract class AbstractRestriction<E, V> implements WhereRestriction<E> {
    private final ExpressionSupplier<E, V> supplier;

    public AbstractRestriction(@Nonnull ExpressionSupplier<E, V> supplier) {
        this.supplier = supplier;
    }

    @Nullable
    @Override
    public Predicate get(@Nonnull CriteriaBuilder builder, @Nonnull From<?, E> from) {
        return get(builder, supplier.getExpression(builder, from));
    }

    protected abstract Predicate get(@Nonnull CriteriaBuilder builder, @Nonnull Expression<V> expression);
}
