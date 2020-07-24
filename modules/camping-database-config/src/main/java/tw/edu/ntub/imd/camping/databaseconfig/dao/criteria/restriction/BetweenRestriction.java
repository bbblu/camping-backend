package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public class BetweenRestriction<E, V extends Comparable<? super V>> extends AbstractRestriction<E, V> {
    private final V firstValue;
    private final V secondValue;

    public BetweenRestriction(@Nonnull ExpressionSupplier<E, V> supplier, @Nonnull V firstValue, @Nonnull V secondValue) {
        super(supplier);
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    @Override
    protected Predicate get(@Nonnull CriteriaBuilder builder, @Nonnull Expression<V> expression) {
        return builder.between(expression, firstValue, secondValue);
    }
}
