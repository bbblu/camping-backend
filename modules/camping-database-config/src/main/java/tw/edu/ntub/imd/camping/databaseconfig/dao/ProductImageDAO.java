package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductImage;

import java.util.List;

@Repository
public interface ProductImageDAO extends BaseDAO<ProductImage, Integer> {
    List<ProductImage> findByProductId(int productId);

    void deleteByProductIdIn(List<Integer> productIdList);

    @Modifying
    @Query("UPDATE ProductImage p SET p.enable = :enable WHERE p.productId in :productIdList")
    void updateEnableByProductIdList(@Param("productIdList") List<Integer> productIdList, @Param("enable") boolean enable);

    @Modifying
    @Query("UPDATE ProductImage p SET p.enable = :enable WHERE p.id in :id")
    void updateEnableById(@Param("id") List<Integer> id, @Param("enable") boolean enable);

    boolean existsByIdAndProductByProductId_ProductGroupByGroupId_CreateAccount(Integer id, String createAccount);
}
