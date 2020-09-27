package tw.edu.ntub.imd.camping.databaseconfig.entity.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Immutable;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 可租借商品列表
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = "productGroupById")
@Entity
@Table(name = "can_borrow_product_group", schema = Config.DATABASE_NAME)
@Immutable
public class CanBorrowProductGroup {
    /**
     * 商品群組編號
     *
     * @since 1.0.0
     */
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer id;

    /**
     * 商品群組名稱
     *
     * @since 1.4.10
     */
    @Column(name = "name", length = 300, nullable = false)
    private String name;

    /**
     * 商品群組封面圖
     *
     * @since 1.0.0
     */
    @Column(name = "cover_image", nullable = false)
    private String coverImage;

    /**
     * 租借價格
     *
     * @since 1.0.0
     */
    @Column(name = "price", nullable = false)
    private Integer price;

    /**
     * 可租借的起始時間
     *
     * @since 1.0.0
     */
    @Column(name = "borrow_start_date", nullable = false)
    private LocalDateTime borrowStartDate;

    /**
     * 可租借的結束時間
     *
     * @since 1.0.0
     */
    @Column(name = "borrow_end_date", nullable = false)
    private LocalDateTime borrowEndDate;

    /**
     * 城市名稱 + ' ' + 區域名稱
     *
     * @see tw.edu.ntub.imd.camping.databaseconfig.entity.City#name
     * @see tw.edu.ntub.imd.camping.databaseconfig.entity.City#areaName
     * @since 1.3.3
     */
    @Column(name = "city", length = 41, nullable = false)
    private String city;

    /**
     * 城市名稱，如臺北市、宜蘭縣
     *
     * @see tw.edu.ntub.imd.camping.databaseconfig.entity.City#name
     * @since 1.3.3
     */
    @Column(name = "city_name", length = 20, nullable = false)
    private String cityName;

    /**
     * 區名稱，如中正區、宜蘭市
     *
     * @see tw.edu.ntub.imd.camping.databaseconfig.entity.City#areaName
     * @since 1.3.3
     */
    @Column(name = "city_area_name", length = 20, nullable = false)
    private String cityAreaName;

    /**
     * 使用者名稱，格式為：暱稱(帳號)
     *
     * @since 1.0.0
     */
    @Column(name = "user_name", length = 152, nullable = false)
    private String userName;

    @Column(name = "product_type")
    private String productTypeArray;

    /**
     * 商品群組
     *
     * @see ProductGroup
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private ProductGroup productGroupById;
}
