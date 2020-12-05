package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tw.edu.ntub.imd.camping.databaseconfig.Config;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 租借退貨紀錄
 *
 * @since 1.8.11
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rental_record_terminate_record", schema = Config.DATABASE_NAME)
public class RentalRecordTerminateRecord {
    /**
     * 流水編號
     *
     * @since 1.8.11
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 紀錄編號
     *
     * @since 1.8.11
     */
    @Column(name = "record_id", nullable = false)
    private Integer recordId;

    /**
     * 退貨原因
     *
     * @since 1.8.11
     */
    @Column(name = "content", length = 300, nullable = false)
    private String content;

    /**
     * 建立者帳號
     *
     * @since 1.8.11
     */
    @CreatedBy
    @Column(name = "create_account", length = 100, nullable = false)
    private String createAccount;

    /**
     * 建立時間
     *
     * @since 1.8.11
     */
    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    /**
     * 租借紀錄
     *
     * @see RentalRecord
     * @since 1.8.11
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", referencedColumnName = "", nullable = false, insertable = false, updatable = false)
    private RentalRecord record;

    /**
     * 建立者
     *
     * @see User
     * @since 1.8.11
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_account", referencedColumnName = "", nullable = false, insertable = false, updatable = false)
    private User creator;
}
