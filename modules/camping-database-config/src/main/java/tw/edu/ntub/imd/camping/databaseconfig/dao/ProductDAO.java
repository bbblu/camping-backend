package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Product;

import java.util.List;

@Repository
public interface ProductDAO extends BaseDAO<Product, Integer> {
    List<Product> findByGroupId(Integer groupId);

    @Modifying
    @Query("UPDATE Product p SET p.enable = :enable WHERE p.groupId = :groupId")
    void updateEnableByGroupId(@Param("groupId") Integer groupId, @Param("enable") boolean enable);
}
