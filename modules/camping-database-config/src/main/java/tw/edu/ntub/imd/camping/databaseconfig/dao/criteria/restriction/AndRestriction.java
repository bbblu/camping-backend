package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;

public class AndRestriction<E> implements WhereRestriction<E> {
    private final WhereRestriction<E>[] restrictionSuppliers;

    @SafeVarargs
    public AndRestriction(WhereRestriction<E>... restrictionSuppliers) {
        this.restrictionSuppliers = restrictionSuppliers;
    }

    @Nullable
    @Override
    public Predicate get(@Nonnull CriteriaBuilder builder, @Nonnull From<?, E> from) {
        return builder.and((Predicate[]) Arrays.stream(restrictionSuppliers).map(supplier -> supplier.get(builder, from)).toArray());
    }
}
