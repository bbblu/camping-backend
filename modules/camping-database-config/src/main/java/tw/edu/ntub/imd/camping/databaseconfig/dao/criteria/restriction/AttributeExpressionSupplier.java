package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.metamodel.SingularAttribute;

public class AttributeExpressionSupplier<J, V> implements ExpressionSupplier<J, V> {
    private final SingularAttribute<? super J, V> attribute;

    public AttributeExpressionSupplier(@Nonnull SingularAttribute<? super J, V> attribute) {
        this.attribute = attribute;
    }

    @Nonnull
    @Override
    public Expression<V> getExpression(@Nonnull CriteriaBuilder builder, @Nonnull From<?, J> from) {
        return from.get(attribute);
    }
}
