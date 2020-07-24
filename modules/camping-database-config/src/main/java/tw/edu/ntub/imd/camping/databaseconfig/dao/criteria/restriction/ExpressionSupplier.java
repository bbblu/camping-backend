package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;

public interface ExpressionSupplier<J, V> {
    @Nonnull
    Expression<V> getExpression(@Nonnull CriteriaBuilder builder, @Nonnull From<?, J> from);
}
