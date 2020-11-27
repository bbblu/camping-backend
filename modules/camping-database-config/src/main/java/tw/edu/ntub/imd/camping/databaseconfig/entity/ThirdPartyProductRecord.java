package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ThirdPartyProductRecordListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners({AuditingEntityListener.class, ThirdPartyProductRecordListener.class})
@Table(name = "third_party_product_record", schema = Config.DATABASE_NAME)
public class ThirdPartyProductRecord {
    /**
     * 流水編號
     *
     * @since 1.8.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 是否啟用(0: 不啟用/ 1: 啟用)
     *
     * @since 1.8.0
     */
    @Getter(AccessLevel.NONE)
    @Column(name = "enable", nullable = false)
    private Boolean enable;

    /**
     * 品牌編號
     *
     * @since 1.8.0
     */
    @Column(name = "brand_id", nullable = false)
    private Integer brandId;

    /**
     * 商品子類型編號
     *
     * @since 1.8.0
     */
    @Column(name = "sub_type", nullable = false)
    private Integer subType;

    /**
     * 價格
     *
     * @since 1.8.0
     */
    @Column(name = "price", nullable = false)
    private Integer price;

    /**
     * 建立者帳號
     *
     * @since 1.8.0
     */
    @CreatedBy
    @Column(name = "create_account", length = 100, nullable = false)
    private String createAccount;

    /**
     * 建立時間
     *
     * @since 1.8.0
     */
    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    /**
     * 商品品牌
     *
     * @see ProductBrand
     * @since 1.8.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProductBrand productBrand;

    /**
     * 商品子類型
     *
     * @see ProductSubType
     * @since 1.8.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_type", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProductSubType productSubType;

    /**
     * 建立者
     *
     * @see User
     * @since 1.8.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User creator;

    /**
     * 是否啟用(0: 不啟用/ 1: 啟用)
     *
     * @since 1.8.0
     */
    public Boolean isEnable() {
        return enable;
    }
}
