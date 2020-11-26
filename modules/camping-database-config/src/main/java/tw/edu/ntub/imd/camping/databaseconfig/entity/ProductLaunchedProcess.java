package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ProductLaunchedProcessListener;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProductLaunchedProcessStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 商品群組上架紀錄表
 *
 * @since 1.0.0
 */
@Data
@Entity
@EntityListeners({AuditingEntityListener.class, ProductLaunchedProcessListener.class})
@Table(name = "product_launched_process", schema = Config.DATABASE_NAME)
public class ProductLaunchedProcess {
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
     * 要上架的商品群組編號
     *
     * @since 1.0.0
     */
    @Column(name = "group_id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer groupId;

    /**
     * 執行狀態(0: 未執行/ 1: 已執行/ 2: 失敗)
     *
     * @see ProductLaunchedProcessStatus
     * @since 1.0.0
     */
    @Enumerated
    @Column(name = "status", length = 1, nullable = false)
    private ProductLaunchedProcessStatus status;

    /**
     * 上架時間
     *
     * @since 1.0.0
     */
    @Column(name = "launched_date", nullable = false)
    private LocalDateTime launchedDate;

    /**
     * 錯誤紀錄
     *
     * @since 1.0.0
     */
    @Column(name = "error_memo")
    private String errorMemo;

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
     * 要上架的商品群組
     *
     * @see ProductGroup
     * @since 1.0.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private ProductGroup productGroupByGroupId;
}
