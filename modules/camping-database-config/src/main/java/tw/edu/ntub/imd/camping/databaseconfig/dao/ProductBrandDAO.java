package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductBrand;

import java.util.Optional;

@Repository
public interface ProductBrandDAO extends BaseDAO<ProductBrand, Integer> {

    Optional<ProductBrand> findByName(String brandName);
}
