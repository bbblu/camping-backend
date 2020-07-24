package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.function;

import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.AttributeExpressionSupplier;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.ConstantExpressionSupplier;

import javax.persistence.metamodel.SingularAttribute;

public final class FunctionUtils {
    private FunctionUtils() {

    }

    public static <E> CriteriaFunction<E, String> left(SingularAttribute<? super E, ?> leftAttribute, int length) {
        return new CriteriaFunction<>(
                "LEFT",
                String.class,
                new AttributeExpressionSupplier<>(leftAttribute),
                new ConstantExpressionSupplier<>(length)
        );
    }
}
