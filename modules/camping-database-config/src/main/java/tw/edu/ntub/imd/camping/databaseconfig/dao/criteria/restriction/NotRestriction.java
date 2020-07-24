package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public class NotRestriction<E> implements WhereRestriction<E> {
    private final WhereRestriction<E> restriction;

    public NotRestriction(WhereRestriction<E> restriction) {
        this.restriction = restriction;
    }

    @Nullable
    @Override
    public Predicate get(@Nonnull CriteriaBuilder builder, @Nonnull From<?, E> from) {
        Predicate predicate = restriction.get(builder, from);
        return predicate != null ? predicate.not() : null;
    }
}
