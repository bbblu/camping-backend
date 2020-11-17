package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.*;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.RentalRecordListener;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 租借紀錄
 *
 * @since 1.0.0
 */
@Data
@Entity
@EntityListeners(RentalRecordListener.class)
@Table(name = "rental_record", schema = Config.DATABASE_NAME)
public class RentalRecord {
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
     * 租借商品群組編號
     *
     * @since 1.0.0
     */
    @Column(name = "product_group_id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer productGroupId;

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    @Getter(AccessLevel.NONE)
    @Column(name = "enable", nullable = false)
    private Boolean enable;

    /**
     * 狀態(0:取消/ 1: 未取貨/ 2:未歸還/ 3:已歸還/ 4: 已檢查)
     *
     * @see RentalRecordStatus
     * @since 1.0.0
     */
    @Enumerated
    @Column(name = "status", length = 1, nullable = false)
    private RentalRecordStatus status;

    /**
     * 信用卡交易編號
     *
     * @since 1.4.0
     */
    @Column(name = "transaction_id", nullable = false)
    private Integer transactionId;

    /**
     * 租借者帳號
     *
     * @since 1.0.0
     */
    @Column(name = "renter_account", length = 100, nullable = false)
    private String renterAccount;

    /**
     * 租借人的信用卡號(只顯示末四碼)
     *
     * @since 1.4.0
     */
    @Column(name = "renter_credit_card_id", length = 16, nullable = false)
    private String renterCreditCardId;

    /**
     * 點選我要租借的時間，即建立時間
     *
     * @since 1.0.0
     */
    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate;

    /**
     * 預計租借起始時間
     *
     * @since 1.0.0
     */
    @Column(name = "borrow_start_date", nullable = false)
    private LocalDateTime borrowStartDate;

    /**
     * 預計租借結束時間
     *
     * @since 1.0.0
     */
    @Column(name = "borrow_end_date", nullable = false)
    private LocalDateTime borrowEndDate;

    /**
     * 同意時間
     *
     * @since 1.6.1
     */
    @Column(name = "agree_date")
    private LocalDateTime agreeDate;

    /**
     * 付款時間
     *
     * @since 1.6.1
     */
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    /**
     * 寄放日期
     *
     * @since 1.6.0
     */
    @Column(name = "placed_date")
    private LocalDateTime placedDate;

    /**
     * 取貨時間
     *
     * @since 1.0.0
     */
    @Column(name = "pick_date")
    private LocalDateTime pickDate;

    /**
     * 歸還時間
     *
     * @since 1.0.0
     */
    @Column(name = "return_date")
    private LocalDateTime returnDate;

    /**
     * 取回時間
     *
     * @since 1.6.0
     */
    @Column(name = "back_date")
    private LocalDateTime backDate;

    /**
     * 取消時間
     *
     * @since 1.0.0
     */
    @Column(name = "cancel_date")
    private LocalDateTime cancelDate;

    /**
     * 最後修改人帳號
     *
     * @since 1.0.0
     */
    @Column(name = "last_modify_account", length = 100, nullable = false)
    private String lastModifyAccount;

    /**
     * 最後更新時間
     *
     * @since 1.0.0
     */
    @Column(name = "last_modify_date", nullable = false)
    private LocalDateTime lastModifyDate;

    /**
     * 租借商品群組
     *
     * @see ProductGroup
     * @since 1.0.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_group_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private ProductGroup productGroupByProductGroupId;

    /**
     * 租借者
     *
     * @see User
     * @since 1.0.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByRenterAccount;

    /**
     * 最後修改人
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
