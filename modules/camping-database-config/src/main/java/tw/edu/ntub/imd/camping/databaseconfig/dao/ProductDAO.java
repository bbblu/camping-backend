package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Product;

import java.util.List;

@Repository
public interface ProductDAO extends BaseDAO<Product, Integer> {
    List<Product> findByGroupId(Integer id);
}
