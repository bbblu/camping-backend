package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria;

import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.restriction.WhereRestriction;

import javax.annotation.Nonnull;

public interface QueryRestrictionHandler<S> {
    QueryRestrictionHandler<S> add(@Nonnull WhereRestriction<S> predicateSupplier);
}
