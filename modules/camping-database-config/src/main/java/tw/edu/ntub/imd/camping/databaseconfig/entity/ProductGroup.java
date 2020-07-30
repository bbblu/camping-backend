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
    @Convert(converter = BooleanTo1And0Converter.class)
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
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "borrow_start_date", nullable = false)
    private DateTimeWrapper borrowStartDate = new LocalDateTimeWrapper();

    /**
     * 可租借的結束時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "borrow_end_date", nullable = false)
    private DateTimeWrapper borrowEndDate;

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
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "create_date", nullable = false)
    private DateTimeWrapper createDate = new LocalDateTimeWrapper();

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
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "last_modify_date", nullable = false)
    private DateTimeWrapper lastModifyDate = new LocalDateTimeWrapper();

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
     * @return 是否啟用(0 : 否 / 1 : 是)
     * @since 1.0.0
     */
    public Boolean isEnable() {
        return enable;
    }
}
