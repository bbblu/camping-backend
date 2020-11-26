package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserBadRecordType;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 使用者不良紀錄
 *
 * @since 1.7.0
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_bad_record", schema = Config.DATABASE_NAME)
public class UserBadRecord {
    /**
     * 流水編號
     *
     * @since 1.7.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 使用者帳號
     *
     * @since 1.7.0
     */
    @Column(name = "user_account", length = 100, nullable = false)
    private String userAccount;

    /**
     * 類型(0: 取消交易/ 1: 退貨/ 2: 終止交易/ 3: 毀損商品)
     *
     * @since 1.7.0
     */
    @Column(name = "type", length = 1, nullable = false)
    private UserBadRecordType type;

    /**
     * 紀錄時間
     *
     * @since 1.7.0
     */
    @CreatedDate
    @Column(name = "record_date", nullable = false)
    private LocalDateTime recordDate;

    /**
     * 使用者
     *
     * @see User
     * @since 1.7.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User user;
}
