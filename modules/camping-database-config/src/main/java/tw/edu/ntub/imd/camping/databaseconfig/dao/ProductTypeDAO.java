package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTypeDAO extends BaseViewDAO<ProductType, Integer> {
    List<ProductType> findByEnableIsTrue();

    Optional<ProductType> findByNameAndEnableIsTrue(String type);
}
