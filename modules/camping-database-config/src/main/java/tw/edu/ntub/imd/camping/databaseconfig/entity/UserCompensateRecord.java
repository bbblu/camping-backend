package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.*;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.UserCompensateRecordListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 使用者賠償紀錄
 *
 * @since 1.7.5
 */
@Data
@Entity
@EntityListeners(UserCompensateRecordListener.class)
@Table(name = "user_compensate_record", schema = Config.DATABASE_NAME)
public class UserCompensateRecord {
    /**
     * 流水編號
     *
     * @since 1.7.5
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 使用者帳號
     *
     * @since 1.7.5
     */
    @Column(name = "user_account", length = 100, nullable = false)
    private String userAccount;

    /**
     * 是否賠償(0: 未賠償/ 1: 已賠償)
     *
     * @since 1.7.5
     */
    @Getter(AccessLevel.NONE)
    @Column(name = "compensated", nullable = false)
    private Boolean compensated;

    /**
     * 要賠償的租借紀錄編號
     *
     * @since 1.7.5
     */
    @Column(name = "rental_record_id", nullable = false)
    private Integer rentalRecordId;

    /**
     * 賠償金額
     *
     * @since 1.7.5
     */
    @Column(name = "compensate_price", nullable = false)
    private Integer compensatePrice;

    /**
     * 交易編號
     *
     * @since 1.7.5
     */
    @Column(name = "transaction_id")
    private Integer transactionId;

    /**
     * 賠償時間
     *
     * @since 1.7.5
     */
    @Column(name = "compensate_date")
    private LocalDateTime compensateDate;

    /**
     * 建立時間
     *
     * @since 1.7.5
     */
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    /**
     * 使用者
     *
     * @see User
     * @since 1.7.5
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User user;

    /**
     * 租借紀錄
     *
     * @since 1.7.5
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_record_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private RentalRecord rentalRecord;

    /**
     * 是否賠償(0: 未賠償/ 1: 已賠償)
     *
     * @since 1.7.5
     */
    public Boolean isCompensated() {
        return compensated;
    }
}
