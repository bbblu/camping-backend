package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.*;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ForgotPasswordTokenListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 忘記密碼驗證碼對照表
 *
 * @since 1.7.6
 */
@Data
@Entity
@EntityListeners(ForgotPasswordTokenListener.class)
@Table(name = "forgot_password_token", schema = Config.DATABASE_NAME)
public class ForgotPasswordToken {
    /**
     * 流水編號
     *
     * @since 1.7.6
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 是否啟用(0: 不啟用/ 1: 啟用)
     *
     * @since 1.7.6
     */
    @Getter(AccessLevel.NONE)
    @Column(name = "enable", nullable = false)
    private Boolean enable;

    /**
     * 忘記密碼的帳號
     *
     * @since 1.7.6
     */
    @Column(name = "user_account", length = 100, nullable = false)
    private String userAccount;

    /**
     * 金鑰，以UUID表示
     *
     * @since 1.7.6
     */
    @Column(name = "token", length = 36, nullable = false)
    private String token;

    /**
     * 過期時間，為建立後七天
     *
     * @since 1.7.6
     */
    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expireDate;

    /**
     * 建立時間
     *
     * @since 1.7.6
     */
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    /**
     * 不啟用時間
     *
     * @since 1.7.6
     */
    @Column(name = "disable_date")
    private LocalDateTime disableDate;

    /**
     * 使用者
     *
     * @see User
     * @since 1.7.6
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User user;

    /**
     * 是否啟用(0: 不啟用/ 1: 啟用)
     *
     * @since 1.7.6
     */
    public Boolean isEnable() {
        return enable;
    }
}
