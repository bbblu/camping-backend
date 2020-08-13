package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductRelatedLink;

import java.util.List;

@Repository
public interface ProductRelatedLinkDAO extends BaseDAO<ProductRelatedLink, Integer> {
    List<ProductRelatedLink> findByProductId(int productId);

    void deleteByProductIdIn(List<Integer> productIdList);

    @Modifying
    @Query("UPDATE ProductRelatedLink p SET p.enable = :enable WHERE p.productId in :productIdList")
    void updateEnableByProductIdList(@Param("productIdList") List<Integer> productIdList, @Param("enable") boolean enable);
}
