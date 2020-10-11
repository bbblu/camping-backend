package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface BaseDAO<E, ID extends Serializable> extends BaseViewDAO<E, ID>, JpaRepository<E, ID> {
    @Nonnull
    @Override
    Optional<E> findById(@Nonnull ID id);

    @Override
    boolean existsById(ID id);
}
