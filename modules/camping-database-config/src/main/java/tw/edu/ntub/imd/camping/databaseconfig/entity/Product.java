package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tw.edu.ntub.birc.common.wrapper.date.DateTimeWrapper;
import tw.edu.ntub.birc.common.wrapper.date.LocalDateTimeWrapper;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.converter.BooleanTo1And0Converter;
import tw.edu.ntub.imd.camping.databaseconfig.converter.DateTimeWrapperConverter;

import javax.persistence.*;

/**
 * 上架商品的詳細內容
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = {
        "productGroupByGroupId",
        "productTypeByType",
        "userByLastModifyAccount"
})
@Entity
@Table(name = "product", schema = Config.DATABASE_NAME)
public class Product {
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
     * 商品群組編號
     *
     * @since 1.0.0
     */
    @Column(name = "group_id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer groupId;

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    @Getter(AccessLevel.NONE)
    @Convert(converter = BooleanTo1And0Converter.class)
    @Column(name = "enable", nullable = false)
    private Boolean enable = true;

    /**
     * 商品類型
     *
     * @since 1.0.0
     */
    @Column(name = "type", nullable = false, columnDefinition = "UNSIGNED")
    private Integer type;

    /**
     * 商品名稱
     *
     * @since 1.0.0
     */
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    /**
     * 數量
     *
     * @since 1.0.0
     */
    @Column(name = "count", nullable = false)
    private Integer count;

    /**
     * 品牌
     *
     * @since 1.0.0
     */
    @Column(name = "brand", length = 100)
    private String brand;

    /**
     * 使用方式
     *
     * @since 1.0.0
     */
    @Column(name = "use_information", columnDefinition = "MEDIUMTEXT")
    private String useInformation;

    /**
     * 損壞賠償
     *
     * @since 1.0.0
     */
    @Column(name = "broken_compensation", length = 200, nullable = false)
    private String brokenCompensation;

    /**
     * 備註
     *
     * @since 1.0.0
     */
    @Column(name = "memo", columnDefinition = "MEDIUMTEXT")
    private String memo;

    /**
     * 最後修改者帳號
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
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "last_modify_date", nullable = false)
    private DateTimeWrapper lastModifyDate = new LocalDateTimeWrapper();

    /**
     * 商品群組
     *
     * @see ProductGroup
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private ProductGroup productGroupByGroupId;

    /**
     * 商品類型
     *
     * @see ProductType
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private ProductType productTypeByType;

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
     * @return 是否啟用(0 : 否 / 1 : 是)
     * @since 1.0.0
     */
    public Boolean isEnable() {
        return enable;
    }
}
