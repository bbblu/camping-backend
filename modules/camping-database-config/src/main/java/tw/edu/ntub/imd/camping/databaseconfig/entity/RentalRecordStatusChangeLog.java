package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 租借紀錄狀態變更紀錄
 *
 * @since 1.6.0
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rental_record_status_change_log", schema = Config.DATABASE_NAME)
@IdClass(RentalRecordStatusChangeLogId.class)
public class RentalRecordStatusChangeLog implements Persistable<RentalRecordStatusChangeLogId> {
    /**
     * 租借紀錄編號
     *
     * @since 1.6.0
     */
    @Id
    @Column(name = "record_id", nullable = false)
    private Integer recordId;

    /**
     * 原先的狀態
     *
     * @since 1.6.0
     */
    @Column(name = "from_status", length = 2, nullable = false)
    private RentalRecordStatus fromStatus;

    /**
     * 變更後的狀態
     *
     * @since 1.6.0
     */
    @Id
    @Column(name = "to_status", length = 2, nullable = false)
    private RentalRecordStatus toStatus;

    /**
     * 變更原因
     *
     * @since 1.6.0
     */
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    /**
     * 變更者帳號
     *
     * @since 1.6.0
     */
    @CreatedBy
    @Column(name = "changer_account", length = 100, nullable = false)
    private String changerAccount;

    /**
     * 變更時間
     *
     * @since 1.6.0
     */
    @CreatedDate
    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate;

    /**
     * 租借紀錄
     *
     * @since 1.6.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private RentalRecord record;

    /**
     * 變更者
     *
     * @since 1.6.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changer_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User changer;

    @Override
    public RentalRecordStatusChangeLogId getId() {
        return new RentalRecordStatusChangeLogId(recordId, toStatus);
    }

    @Override
    public Boolean getSave() {
        return true;
    }

    @Override
    public void setSave(Boolean isSave) {

    }
}
