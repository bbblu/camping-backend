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
 * 商品圖
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = "productByProductId")
@Entity
@Table(name = "product_image", schema = Config.DATABASE_NAME)
public class ProductImage {
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
     * 商品編號
     *
     * @since 1.0.0
     */
    @Column(name = "product_id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer productId;

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
     * 圖片網址
     *
     * @since 1.0.0
     */
    @Column(name = "url", nullable = false)
    private String url;

    /**
     * 建立時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "create_date", nullable = false)
    private DateTimeWrapper createDate = new LocalDateTimeWrapper();

    /**
     * 最後修改時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "last_modify_date", nullable = false)
    private DateTimeWrapper lastModifyDate = new LocalDateTimeWrapper();

    /**
     * 商品
     *
     * @see Product
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private Product productByProductId;


    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @return 是否啟用(0 : 否 / 1 : 是)
     * @since 1.0.0
     */
    public Boolean getEnable() {
        return enable;
    }
}
