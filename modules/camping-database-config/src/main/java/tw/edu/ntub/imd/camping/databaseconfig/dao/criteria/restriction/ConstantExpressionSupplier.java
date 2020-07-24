package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;

public class ConstantExpressionSupplier<J, V> implements ExpressionSupplier<J, V> {
    private final V value;

    public ConstantExpressionSupplier(V value) {
        this.value = value;
    }

    @Nonnull
    @Override
    public Expression<V> getExpression(@Nonnull CriteriaBuilder builder, @Nonnull From<?, J> from) {
        return builder.literal(value);
    }
}
