package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductSubType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSubTypeDAO extends BaseDAO<ProductSubType, Integer> {

    List<ProductSubType> findByTypeAndEnableIsTrue(int type, Sort sort);

    Optional<ProductSubType> findByTypeAndName(int type, String subTypeName);
}
