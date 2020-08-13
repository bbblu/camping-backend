package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductType;

import java.util.List;

@Repository
public interface ProductTypeDAO extends BaseViewDAO<ProductType, Integer> {
    List<ProductType> findByEnableIsTrue();
}
