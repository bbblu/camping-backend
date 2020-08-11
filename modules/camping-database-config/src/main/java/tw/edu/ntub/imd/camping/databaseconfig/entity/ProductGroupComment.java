package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ProductGroupCommentListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 商品群組評價
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = {
        "productGroupByGroupId",
        "userByCommentAccount"
})
@Entity
@EntityListeners(ProductGroupCommentListener.class)
@Table(name = "product_group_comment", schema = Config.DATABASE_NAME)
public class ProductGroupComment {
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
     * 評價(0 ~ 5)
     *
     * @since 1.0.0
     */
    @Column(name = "comment", nullable = false, columnDefinition = "UNSIGNED")
    private Byte comment;

    /**
     * 評價者帳號
     *
     * @since 1.0.0
     */
    @Column(name = "comment_account", length = 100, nullable = false)
    private String commentAccount;

    /**
     * 評價時間
     *
     * @since 1.0.0
     */
    @Column(name = "comment_date", nullable = false)
    private LocalDateTime commentDate;

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
     * 評價者
     *
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByCommentAccount;
}
