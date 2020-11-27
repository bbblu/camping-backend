package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ProductSubTypeListener;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 商品子類型
 *
 * @since 1.8.0
 */
@Data
@Entity
@EntityListeners({AuditingEntityListener.class, ProductSubTypeListener.class})
@Table(name = "product_sub_type", schema = Config.DATABASE_NAME)
public class ProductSubType {
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
     * 商品類型編號
     *
     * @since 1.8.0
     */
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 子類型名稱
     *
     * @since 1.8.0
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

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
     * 商品類型
     *
     * @see ProductType
     * @since 1.8.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ProductType productType;

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
