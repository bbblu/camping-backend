package tw.edu.ntub.imd.camping.databaseconfig.entity.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;
import tw.edu.ntub.imd.camping.databaseconfig.Config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 第三方商品紀錄管理頁資料整理
 *
 * @since 1.8.3
 */
@Data
@Entity
@Table(name = "third_party_product_record_index", schema = Config.DATABASE_NAME)
@Immutable
public class ThirdPartyProductRecordIndex {
    /**
     * 編號
     *
     * @since 1.8.3
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 品牌編號
     *
     * @since 1.8.3
     */
    @Column(name = "brand_id", nullable = false)
    private Integer brandId;

    /**
     * 品牌名稱
     *
     * @since 1.8.3
     */
    @Column(name = "brand_name", length = 50, nullable = false)
    private String brandName;

    /**
     * 商品類型
     *
     * @since 1.8.3
     */
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 商品類型名稱
     *
     * @since 1.8.3
     */
    @Column(name = "type_name", length = 50, nullable = false)
    private String typeName;

    /**
     * 商品子類型
     *
     * @since 1.8.3
     */
    @Column(name = "sub_type", nullable = false)
    private Integer subType;

    /**
     * 商品子類型的名稱
     *
     * @since 1.8.3
     */
    @Column(name = "sub_type_name", length = 50, nullable = false)
    private String subTypeName;

    /**
     * 價格
     *
     * @since 1.8.3
     */
    @Column(name = "price", nullable = false)
    private Integer price;
}
