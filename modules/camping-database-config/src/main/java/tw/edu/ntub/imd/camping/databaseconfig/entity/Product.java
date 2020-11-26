package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ProductListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 上架商品的詳細內容
 *
 * @since 1.0.0
 */
@Data
@Entity
@EntityListeners({AuditingEntityListener.class, ProductListener.class})
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
    @Column(name = "enable", nullable = false)
    private Boolean enable;

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
     * 外觀狀況
     *
     * @since 1.4.5
     */
    @Column(name = "appearance", length = 500, nullable = false)
    private String appearance;

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
     * 相關連結
     *
     * @since 1.4.8
     */
    @Column(name = "related_link", length = 600)
    private String relatedLink;

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
    @LastModifiedBy
    @Column(name = "last_modify_account", length = 100, nullable = false)
    private String lastModifyAccount;

    /**
     * 最後修改時間
     *
     * @since 1.0.0
     */
    @LastModifiedDate
    @Column(name = "last_modify_date", nullable = false)
    private LocalDateTime lastModifyDate;

    /**
     * 商品群組
     *
     * @see ProductGroup
     * @since 1.0.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private ProductGroup productGroupByGroupId;

    /**
     * 商品類型
     *
     * @see ProductType
     * @since 1.0.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private ProductType productTypeByType;

    /**
     * 最後修改者
     *
     * @see User
     * @since 1.0.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
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
