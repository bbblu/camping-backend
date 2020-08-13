package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductRelatedLink;

import java.util.List;

@Repository
public interface ProductRelatedLinkDAO extends BaseDAO<ProductRelatedLink, Integer> {
    List<ProductRelatedLink> findByProductId(int productId);
}
