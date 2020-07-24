package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public class LikeRestriction<E> extends AbstractRestriction<E, String> {
    private final String pattern;

    public LikeRestriction(@Nonnull ExpressionSupplier<E, String> supplier, @Nonnull String pattern) {
        super(supplier);
        this.pattern = pattern;
    }

    @Override
    protected Predicate get(@Nonnull CriteriaBuilder builder, @Nonnull Expression<String> expression) {
        return builder.like(expression, pattern);
    }
}
