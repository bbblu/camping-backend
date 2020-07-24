package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public interface WhereRestriction<J> {
    @Nullable
    Predicate get(@Nonnull CriteriaBuilder builder, @Nonnull From<?, J> from);
}
