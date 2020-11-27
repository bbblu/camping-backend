package tw.edu.ntub.imd.camping.databaseconfig.entity.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;
import tw.edu.ntub.imd.camping.databaseconfig.Config;

import javax.persistence.*;

/**
 * 可計算建議售價的品牌
 *
 * @since 1.8.1
 */
@Data
@Entity
@Table(name = "brand_product_type", schema = Config.DATABASE_NAME)
@IdClass(BrandProductTypeId.class)
@Immutable
public class BrandProductType {
    /**
     * 品牌
     *
     * @since 1.8.1
     */
    @Id
    @Column(name = "brand", nullable = false)
    private Integer brand;

    /**
     * 商品類型
     *
     * @since 1.8.1
     */
    @Id
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 類型名稱
     *
     * @since 1.8.1
     */
    @Column(name = "type_name", length = 50, nullable = false)
    private String typeName;

    /**
     * 商品子類型
     *
     * @since 1.8.1
     */
    @Id
    @Column(name = "sub_type", nullable = false)
    private Integer subType;

    /**
     * 子類型名稱
     *
     * @since 1.8.1
     */
    @Column(name = "sub_type_name", length = 50, nullable = false)
    private String subTypeName;
}
