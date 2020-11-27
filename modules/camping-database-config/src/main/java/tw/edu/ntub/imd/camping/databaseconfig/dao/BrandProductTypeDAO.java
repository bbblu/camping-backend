package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductSubType;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductType;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.BrandProductType;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.BrandProductTypeId;

import java.util.List;

@Repository
public interface BrandProductTypeDAO extends BaseViewDAO<BrandProductType, BrandProductTypeId> {
    @Query(
            "SELECT new tw.edu.ntub.imd.camping.databaseconfig.entity.ProductType(b.type, b.typeName) " +
                    "FROM BrandProductType b " +
                    "WHERE b.brand = :brand " +
                    "GROUP BY b.type " +
                    "ORDER BY b.type"
    )
    List<ProductType> findTypeByBrand(@Param("brand") Integer brand);

    @Query(
            "SELECT new tw.edu.ntub.imd.camping.databaseconfig.entity.ProductSubType(b.subType, b.subTypeName) " +
                    "FROM BrandProductType b " +
                    "WHERE b.brand = :brand AND b.type = :type " +
                    "ORDER BY b.subType"
    )
    List<ProductSubType> findSubTypeByBrandAndType(@Param("brand") int brand, @Param("type") int type);
}
