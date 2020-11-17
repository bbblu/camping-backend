package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.RentalRecordCheckLogListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 租借紀錄出租方商品檢查紀錄
 *
 * @since 1.6.1
 */
@Data
@NoArgsConstructor
@Entity
@EntityListeners(RentalRecordCheckLogListener.class)
@Table(name = "rental_record_check_log", schema = Config.DATABASE_NAME)
public class RentalRecordCheckLog {
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
    @Column(name = "record_id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer recordId;

    /**
     * 檢查紀錄
     *
     * @since 1.6.1
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * 檢查者帳號
     *
     * @since 1.6.1
     */
    @Column(name = "checker_account", length = 100, nullable = false)
    private String checkerAccount;

    /**
     * 檢查時間
     *
     * @since 1.6.1
     */
    @Column(name = "check_date", nullable = false)
    private LocalDateTime checkDate;

    /**
     * 租借紀錄
     *
     * @see RentalRecord
     * @since 1.6.1
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private RentalRecord rentalRecord;

    /**
     * 檢查者
     *
     * @see User
     * @since 1.6.1
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checker_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User checker;

    public RentalRecordCheckLog(Integer recordId, String content) {
        this.recordId = recordId;
        this.content = content;
    }
}
