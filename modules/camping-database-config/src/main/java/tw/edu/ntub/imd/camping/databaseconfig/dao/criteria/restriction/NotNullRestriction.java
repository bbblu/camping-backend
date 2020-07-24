package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;

public class NotNullRestriction<E> implements WhereRestriction<E> {
    private final SingularAttribute<? super E, ?> attribute;

    public NotNullRestriction(SingularAttribute<? super E, ?> attribute) {
        this.attribute = attribute;
    }

    @Nullable
    @Override
    public Predicate get(@Nonnull CriteriaBuilder builder, @Nonnull From<?, E> from) {
        return builder.isNotNull(from.get(attribute));
    }
}
