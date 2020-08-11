package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ContactInformationListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 聯絡方式紀錄表
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = "userByUserAccount")
@Entity
@EntityListeners(ContactInformationListener.class)
@Table(name = "contact_information", schema = Config.DATABASE_NAME)
public class ContactInformation {
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
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    @Getter(AccessLevel.NONE)
    @Column(name = "enable", nullable = false)
    private Boolean enable;

    /**
     * 該聯絡方式對應的使用者帳號
     *
     * @since 1.0.0
     */
    @Column(name = "user_account", length = 100, nullable = false)
    private String userAccount;

    /**
     * 聯絡方式
     *
     * @since 1.0.0
     */
    @Column(name = "content", length = 150, nullable = false)
    private String content;

    /**
     * 建立時間
     *
     * @since 1.0.0
     */
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    /**
     * 最後修改時間
     *
     * @since 1.0.0
     */
    @Column(name = "last_modify_date", nullable = false)
    private LocalDateTime lastModifyDate;

    /**
     * 使用者
     *
     * @see User
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByUserAccount;

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    public Boolean isEnable() {
        return enable;
    }
}
