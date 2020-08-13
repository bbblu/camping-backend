package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;

@Repository
public interface ProductGroupDAO extends BaseDAO<ProductGroup, Integer> {
    @Modifying
    @Query("UPDATE ProductGroup p SET p.enable = :enable WHERE p.id = :id")
    void updateEnableById(@Param("id") Integer id, @Param("enable") boolean enable);
}
