package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 租借紀錄商品檢查紀錄
 *
 * @since 1.6.1
 */
@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
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
     * 租借紀錄檢查時的狀態
     *
     * @since 1.7.0
     */
    @Column(name = "record_status", length = 2, nullable = false)
    private RentalRecordStatus recordStatus;

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
    @CreatedBy
    @Column(name = "checker_account", length = 100, nullable = false)
    private String checkerAccount;

    /**
     * 檢查時間
     *
     * @since 1.6.1
     */
    @CreatedDate
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

    public RentalRecordCheckLog(Integer recordId, RentalRecordStatus recordStatus, String content) {
        this.recordId = recordId;
        this.recordStatus = recordStatus;
        this.content = content;
    }
}
