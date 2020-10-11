package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.RentalRecordCancelListener;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordCancelStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 租借紀錄取消紀錄表
 *
 * @since 1.5.0
 */
@Data
@Entity
@EntityListeners(RentalRecordCancelListener.class)
@Table(name = "rental_record_cancel", schema = Config.DATABASE_NAME)
public class RentalRecordCancel {
    /**
     * 流水編號
     *
     * @since 1.5.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 租借紀錄編號
     *
     * @since 1.5.0
     */
    @Column(name = "record_id", nullable = false)
    private Integer recordId;

    /**
     * 取消狀態(0: 等待買方同意/ 1: 等待出借方同意/ 2: 取消被拒絕/ 3: 已取消)
     *
     * @since 1.5.0
     */
    @Enumerated
    @Column(name = "status", length = 1, nullable = false)
    private RentalRecordCancelStatus status;

    /**
     * 取消原因
     *
     * @since 1.5.0
     */
    @Column(name = "cancel_detail", length = 400, nullable = false)
    private String cancelDetail;

    /**
     * 出借方同意時間
     *
     * @since 1.5.0
     */
    @Column(name = "product_owner_agree_date")
    private LocalDateTime productOwnerAgreeDate;

    /**
     * 買方同意時間
     *
     * @since 1.5.0
     */
    @Column(name = "renter_agree_date")
    private LocalDateTime renterAgreeDate;

    /**
     * 拒絕取消原因
     *
     * @since 1.5.0
     */
    @Column(name = "denied_detail", length = 400)
    private String deniedDetail;

    /**
     * 建立者帳號
     *
     * @since 1.5.0
     */
    @Column(name = "create_account", length = 100, nullable = false)
    private String createAccount;

    /**
     * 最後修改者帳號
     *
     * @since 1.5.0
     */
    @Column(name = "last_modify_account", length = 100, nullable = false)
    private String lastModifyAccount;

    /**
     * 最後修改時間
     *
     * @since 1.5.0
     */
    @Column(name = "last_modify_date", nullable = false)
    private LocalDateTime lastModifyDate;

    /**
     * 租借紀錄
     *
     * @see RentalRecord
     * @since 1.5.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private RentalRecord rentalRecord;

    /**
     * 建立者
     *
     * @see User
     * @since 1.5.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User createUser;

    /**
     * 最後修改者
     *
     * @see User
     * @since 1.5.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modify_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User lastModifyUser;
}
