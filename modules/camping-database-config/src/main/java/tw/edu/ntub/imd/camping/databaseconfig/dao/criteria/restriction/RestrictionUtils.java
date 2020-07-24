package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.function.CriteriaFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;

public final class RestrictionUtils {
    private RestrictionUtils() {

    }

    public static <E> EqualRestriction<E, Boolean> isTrue(@Nonnull SingularAttribute<? super E, Boolean> attribute) {
        return equal(attribute, true);
    }

    public static <E> EqualRestriction<E, Boolean> isFalse(@Nonnull SingularAttribute<? super E, Boolean> attribute) {
        return equal(attribute, false);
    }

    public static <E, V> EqualRestriction<E, V> equal(@Nonnull SingularAttribute<? super E, V> attribute, @Nullable V value) {
        return new EqualRestriction<>(new AttributeExpressionSupplier<>(attribute), value);
    }

    public static <E, V> EqualRestriction<E, V> equal(@Nonnull CriteriaFunction<E, V> function, @Nullable V value) {
        return new EqualRestriction<>(function, value);
    }

    public static <E, V> EqualRestriction<E, V> equalIgnoreCase(@Nonnull SingularAttribute<? super E, V> attribute, @Nullable V value) {
        EqualRestriction<E, V> restriction = equal(attribute, value);
        restriction.ignoreCase();
        return restriction;
    }

    public static <E, V> EqualRestriction<E, V> equalIgnoreCase(@Nonnull CriteriaFunction<E, V> function, @Nullable V value) {
        EqualRestriction<E, V> restriction = equal(function, value);
        restriction.ignoreCase();
        return restriction;
    }

    public static <E, V> NotEqualRestriction<E, V> notEqual(@Nonnull SingularAttribute<? super E, V> attribute, @Nullable V value) {
        return new NotEqualRestriction<>(new AttributeExpressionSupplier<>(attribute), value);
    }

    public static <E, V> NotEqualRestriction<E, V> notEqual(@Nonnull CriteriaFunction<E, V> function, @Nullable V value) {
        return new NotEqualRestriction<>(function, value);
    }

    public static <E, V> NotEqualRestriction<E, V> notEqualIgnoreCase(@Nonnull SingularAttribute<? super E, V> attribute, @Nullable V value) {
        NotEqualRestriction<E, V> restriction = notEqual(attribute, value);
        restriction.ignoreCase();
        return restriction;
    }

    public static <E, V> NotEqualRestriction<E, V> notEqualIgnoreCase(@Nonnull CriteriaFunction<E, V> function, @Nullable V value) {
        NotEqualRestriction<E, V> restriction = notEqual(function, value);
        restriction.ignoreCase();
        return restriction;
    }

    public static <E> EqualRestriction<E, ?> isNull(@Nonnull SingularAttribute<? super E, ?> attribute) {
        return equal(attribute, null);
    }

    public static <E> NotNullRestriction<E> isNotNull(@Nonnull SingularAttribute<? super E, ?> attribute) {
        return new NotNullRestriction<>(attribute);
    }

    public static <E, V> InRestriction<E, V> in(@Nonnull CriteriaFunction<E, V> function, @Nullable Collection<V> value) {
        return new InRestriction<>(function, value);
    }

    public static <E, V> InRestriction<E, V> in(@Nonnull SingularAttribute<? super E, V> attribute, @Nullable Collection<V> value) {
        return new InRestriction<>(new AttributeExpressionSupplier<>(attribute), value);
    }

    @SafeVarargs
    public static <E, V> InRestriction<E, V> in(@Nonnull SingularAttribute<? super E, V> attribute, @Nullable V... value) {
        return new InRestriction<>(new AttributeExpressionSupplier<>(attribute), value);
    }

    @SafeVarargs
    public static <E, V> InRestriction<E, V> in(@Nonnull CriteriaFunction<E, V> function, @Nullable V... value) {
        return new InRestriction<>(function, value);
    }

    public static <E, V extends Comparable<? super V>> BetweenRestriction<E, V> between(
            @Nonnull SingularAttribute<? super E, V> attribute, @Nonnull V firstValue, @Nonnull V secondValue) {
        return new BetweenRestriction<>(new AttributeExpressionSupplier<>(attribute), firstValue, secondValue);
    }

    public static <E, V extends Comparable<? super V>> BetweenRestriction<E, V> between(
            @Nonnull CriteriaFunction<E, V> function, @Nonnull V firstValue, @Nonnull V secondValue) {
        return new BetweenRestriction<>(function, firstValue, secondValue);
    }

    public static <E, V extends Comparable<? super V>> CompareRestriction<E, V> greaterThan(
            @Nonnull SingularAttribute<? super E, V> attribute, @Nonnull V value) {
        return new CompareRestriction<>(new AttributeExpressionSupplier<>(attribute), value, CompareRestriction.Operator.GREATER);
    }

    public static <E, V extends Comparable<? super V>> CompareRestriction<E, V> greaterThan(
            @Nonnull CriteriaFunction<E, V> function, @Nonnull V value) {
        return new CompareRestriction<>(function, value, CompareRestriction.Operator.GREATER);
    }

    public static <E, V extends Comparable<? super V>> CompareRestriction<E, V> greaterThanOrEqual(
            @Nonnull SingularAttribute<? super E, V> attribute, @Nonnull V value) {
        return new CompareRestriction<>(new AttributeExpressionSupplier<>(attribute), value, CompareRestriction.Operator.GREATER_OR_EQUAL);
    }

    public static <E, V extends Comparable<? super V>> CompareRestriction<E, V> greaterThanOrEqual(
            @Nonnull CriteriaFunction<E, V> function, @Nonnull V value) {
        return new CompareRestriction<>(function, value, CompareRestriction.Operator.GREATER_OR_EQUAL);
    }

    public static <E, V extends Comparable<? super V>> CompareRestriction<E, V> lessThan(
            @Nonnull SingularAttribute<? super E, V> attribute, @Nonnull V value) {
        return new CompareRestriction<>(new AttributeExpressionSupplier<>(attribute), value, CompareRestriction.Operator.LESS);
    }

    public static <E, V extends Comparable<? super V>> CompareRestriction<E, V> lessThan(
            @Nonnull CriteriaFunction<E, V> function, @Nonnull V value) {
        return new CompareRestriction<>(function, value, CompareRestriction.Operator.LESS);
    }

    public static <E, V extends Comparable<? super V>> CompareRestriction<E, V> lessThanOrEqual(
            @Nonnull SingularAttribute<? super E, V> attribute, @Nonnull V value) {
        return new CompareRestriction<>(new AttributeExpressionSupplier<>(attribute), value, CompareRestriction.Operator.LESS_OR_EQUAL);
    }

    public static <E, V extends Comparable<? super V>> CompareRestriction<E, V> lessThanOrEqual(
            @Nonnull CriteriaFunction<E, V> function, @Nonnull V value) {
        return new CompareRestriction<>(function, value, CompareRestriction.Operator.LESS_OR_EQUAL);
    }

    public static <E> LikeRestriction<E> like(SingularAttribute<E, String> attribute, String pattern) {
        return new LikeRestriction<>(new AttributeExpressionSupplier<>(attribute), pattern);
    }

    public static <E> LikeRestriction<E> like(CriteriaFunction<E, String> function, String pattern) {
        return new LikeRestriction<>(function, pattern);
    }

    public static <E> NotRestriction<E> not(WhereRestriction<E> restriction) {
        return new NotRestriction<>(restriction);
    }

    @SafeVarargs
    public static <E> AndRestriction<E> and(WhereRestriction<E>... restrictions) {
        return new AndRestriction<>(restrictions);
    }

    @SafeVarargs
    public static <E> OrRestriction<E> or(WhereRestriction<E>... restrictions) {
        return new OrRestriction<>(restrictions);
    }
}
