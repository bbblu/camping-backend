package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductImage;

import java.util.List;

@Repository
public interface ProductImageDAO extends BaseDAO<ProductImage, Integer> {
    List<ProductImage> findByProductId(int productId);

    void deleteByProductIdIn(List<Integer> productIdList);
}
