package tw.edu.ntub.imd.camping.databaseconfig.entity.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;
import tw.edu.ntub.imd.camping.databaseconfig.Config;

import javax.persistence.*;

/**
 * 建議設價查詢表
 *
 * @since 1.8.0
 */
@Data
@Entity
@Table(name = "recommend_product_price", schema = Config.DATABASE_NAME)
@IdClass(RecommendProductPriceId.class)
@Immutable
public class RecommendProductPrice {
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
     * @since 1.8.0
     */
    @Id
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 商品子類型
     *
     * @since 1.8.0
     */
    @Id
    @Column(name = "sub_type", nullable = false)
    private Integer subType;

    /**
     * 統計個數
     *
     * @since 1.8.0
     */
    @Column(name = "count", nullable = false)
    private Integer count;

    /**
     * 建議售價
     *
     * @since 1.8.0
     */
    @Column(name = "price", nullable = false, precision = 14, scale = 4)
    private Double price;
}
