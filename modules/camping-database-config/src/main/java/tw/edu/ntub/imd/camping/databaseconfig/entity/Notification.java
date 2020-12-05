package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.NotificationListener;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.NotificationType;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 通知
 *
 * @since 1.6.1
 */
@Data
@Entity
@EntityListeners({AuditingEntityListener.class, NotificationListener.class})
@Table(name = "notification", schema = Config.DATABASE_NAME)
public class Notification {
    /**
     * 流水編號
     *
     * @since 1.6.1
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 租借紀錄編號
     *
     * @since 1.6.1
     */
    @Column(name = "rental_record_id", nullable = false)
    private Integer rentalRecordId;

    /**
     * 通知類型
     * <ul>
     * <li>0: 租借</li>
     * <li>1: 租借成功</li>
     * <li>2: 租借遭拒</li>
     * <li>3: 付款成功</li>
     * <li>4: 付款失敗</li>
     * <li>5: 對方付款</li>
     * <li>6: 取消成功</li>
     * <li>7: 對方取消</li>
     * <li>8: 物品退貨</li>
     * <li>9: 物品送達</li>
     * <li>10: 物品歸還</li>
     * <li>11: 評價邀請</li>
     * </ul>
     *
     * @since 1.6.1
     */
    @Enumerated
    @Column(name = "type", length = 2, nullable = false)
    private NotificationType type;

    /**
     * 通知對象帳號
     *
     * @since 1.6.1
     */
    @Column(name = "user_account", length = 100, nullable = false)
    private String userAccount;

    /**
     * 通知內容
     *
     * @since 1.6.1
     */
    @Column(name = "content", length = 400, nullable = false)
    private String content;

    /**
     * 發送時間
     *
     * @since 1.6.1
     */
    @CreatedDate
    @Column(name = "send_date", nullable = false)
    private LocalDateTime sendDate;

    /**
     * 已讀時間
     *
     * @since 1.6.1
     */
    @Column(name = "read_date")
    private LocalDateTime readDate;

    /**
     * 租借紀錄
     *
     * @see RentalRecord
     * @since 1.6.1
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_record_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private RentalRecord rentalRecord;

    /**
     * 通知對象
     *
     * @see User
     * @since 1.6.1
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User user;
}
