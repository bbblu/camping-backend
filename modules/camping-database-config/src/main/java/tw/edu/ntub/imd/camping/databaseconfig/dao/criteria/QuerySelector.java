package tw.edu.ntub.imd.camping.databaseconfig.dao.criteria;

import tw.edu.ntub.imd.camping.databaseconfig.dto.Pager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface QuerySelector<E, R> extends
        QuerySelectionHandler<E>,
        QueryRestrictionHandler<E>,
        QueryJoinHandler<E>,
        QueryGroupHandler<E>,
        QueryOrderHandler<E> {
    @Nonnull
    List<R> getResultList();

    @Nonnull
    List<R> getResultList(Pager pager);

    Optional<R> getSingleResult();
}
