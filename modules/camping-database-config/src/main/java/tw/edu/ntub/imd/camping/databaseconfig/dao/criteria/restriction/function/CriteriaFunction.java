package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.function;

import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.AttributeExpressionSupplier;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.ExpressionSupplier;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Arrays;

public class CriteriaFunction<J, V> implements ExpressionSupplier<J, V> {
    private final String functionName;
    private final Class<V> returnType;
    private final ExpressionSupplier<J, ?>[] argumentArray;

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public CriteriaFunction(String functionName, Class<V> returnType, SingularAttribute<J, ?>... argumentArray) {
        this(functionName, returnType, (ExpressionSupplier<J, ?>[]) Arrays.stream(argumentArray).map(AttributeExpressionSupplier::new).toArray());
    }

    @SafeVarargs
    public CriteriaFunction(String functionName, Class<V> returnType, ExpressionSupplier<J, ?>... argumentArray) {
        this.functionName = functionName;
        this.returnType = returnType;
        this.argumentArray = argumentArray;
    }

    @Nonnull
    @Override
    public Expression<V> getExpression(@Nonnull CriteriaBuilder builder, @Nonnull From<?, J> from) {
        return argumentArray != null ?
                builder.function(
                        functionName,
                        returnType,
                        (Expression<?>[]) Arrays.stream(argumentArray)
                                .map(eExpressionSupplier -> eExpressionSupplier.getExpression(builder, from))
                                .toArray()
                ) :
                builder.function(functionName, returnType);
    }
}
