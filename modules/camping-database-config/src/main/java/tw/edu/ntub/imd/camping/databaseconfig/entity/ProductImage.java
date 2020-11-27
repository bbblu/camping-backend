package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ProductImageListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 商品圖
 *
 * @since 1.0.0
 */
@Data
@Entity
@EntityListeners({AuditingEntityListener.class, ProductImageListener.class})
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
    @Column(name = "enable", nullable = false)
    private Boolean enable;

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
    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    /**
     * 最後修改時間
     *
     * @since 1.0.0
     */
    @LastModifiedDate
    @Column(name = "last_modify_date", nullable = false)
    private LocalDateTime lastModifyDate;

    /**
     * 商品
     *
     * @see Product
     * @since 1.0.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private Product productByProductId;

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    public Boolean isEnable() {
        return enable;
    }
}
