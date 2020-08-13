package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ProductGroupListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 上架商品群組
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = {
        "contactInformationByContactInformationId",
        "userByCreateAccount",
        "userByLastModifyAccount"
})
@Entity
@EntityListeners(ProductGroupListener.class)
@Table(name = "product_group", schema = Config.DATABASE_NAME)
public class ProductGroup {
    /**
     * 流水編號
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer id;

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    @Getter(AccessLevel.NONE)
    @Column(name = "enable", nullable = false)
    private Boolean enable;

    /**
     * 商品群組名稱
     *
     * @since 1.0.0
     */
    @Column(name = "name", length = 300, nullable = false)
    private String name;

    /**
     * 封面圖
     *
     * @since 1.0.0
     */
    @Column(name = "cover_image", nullable = false)
    private String coverImage;

    /**
     * 城市名稱，如臺北市、宜蘭縣
     *
     * @see City#name
     * @since 1.3.2
     */
    @Column(name = "city_name", length = 20, nullable = false)
    private String cityName;

    /**
     * 區名稱，如中正區、宜蘭市
     *
     * @see City#areaName
     * @since 1.3.2
     */
    @Column(name = "city_area_name", length = 20, nullable = false)
    private String cityAreaName;

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
     * 聯絡方式編號
     *
     * @since 1.0.0
     */
    @Column(name = "contact_information_id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer contactInformationId;

    /**
     * 商品上架者帳號
     *
     * @since 1.0.0
     */
    @Column(name = "create_account", length = 100, nullable = false)
    private String createAccount;

    /**
     * 商品上架時間
     *
     * @since 1.0.0
     */
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    /**
     * 最後修改者的帳號
     *
     * @since 1.0.0
     */
    @Column(name = "last_modify_account", length = 100, nullable = false)
    private String lastModifyAccount;

    /**
     * 最後修改時間
     *
     * @since 1.0.0
     */
    @Column(name = "last_modify_date", nullable = false)
    private LocalDateTime lastModifyDate;

    /**
     * 可租借城市
     *
     * @see City
     * @since 1.3.2
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "city_name", referencedColumnName = "name", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "city_area_name", referencedColumnName = "area_name", nullable = false, insertable = false, updatable = false)
    })
    private City city;

    /**
     * 聯絡方式
     *
     * @see ContactInformation
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_information_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private ContactInformation contactInformationByContactInformationId;

    /**
     * 商品上架者
     *
     * @see User
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByCreateAccount;

    /**
     * 最後修改者
     *
     * @see User
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modify_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByLastModifyAccount;

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    public Boolean isEnable() {
        return enable;
    }
}
