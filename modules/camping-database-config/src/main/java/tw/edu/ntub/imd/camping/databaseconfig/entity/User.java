package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.*;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.UserListener;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Experience;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Gender;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserRoleEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 使用者
 *
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(exclude = {"userRoleByRoleId", "userByLastModifyAccount"})
@Entity
@EntityListeners(UserListener.class)
@Table(name = "user", schema = Config.DATABASE_NAME)
public class User implements Persistable<String> {
    @Transient
    private boolean save;
    /**
     * 使用者帳號
     *
     * @since 1.0.0
     */
    @Id
    @Column(name = "account", length = 100, nullable = false)
    private String account;

    /**
     * 使用者密碼，採用BCrypt格式編碼
     *
     * @since 1.0.0
     */
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    /**
     * 使用者權限(3為一般使用者)
     *
     * @see UserRoleEnum
     * @since 1.0.0
     */
    @Column(name = "role_id", nullable = false, columnDefinition = "UNSIGNED")
    private UserRoleEnum roleId;

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    @Getter(AccessLevel.NONE)
    @Column(name = "enable", nullable = false)
    private Boolean enable;

    /**
     * 露營經驗(0: 新手/ 1: 有過幾次經驗)
     *
     * @see Experience
     * @since 1.0.0
     */
    @Enumerated
    @Column(name = "experience", nullable = false)
    private Experience experience;

    /**
     * 使用者姓氏
     *
     * @since 1.0.0
     */
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    /**
     * 使用者名稱
     *
     * @since 1.0.0
     */
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    /**
     * 暱稱
     *
     * @since 1.0.0
     */
    @Column(name = "nick_name", length = 50, nullable = false)
    private String nickName;

    /**
     * 使用者性別(0: 男/ 1: 女/ 2: 未提供)
     *
     * @see Gender
     * @since 1.0.0
     */
    @Enumerated
    @Column(name = "gender", length = 1, nullable = false)
    private Gender gender;

    /**
     * 手機號碼
     *
     * @since 1.5.2
     */
    @Column(name = "cell_phone", length = 10, nullable = false)
    private String cellPhone;

    /**
     * 電子信箱
     *
     * @since 1.0.0
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 使用者住址
     *
     * @since 1.0.0
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * 生日
     *
     * @since 1.0.0
     */
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    /**
     * 建立時間
     *
     * @since 1.0.0
     */
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    /**
     * 最後修改者帳號
     *
     * @since 1.0.0
     */
    @Column(name = "last_modify_account", length = 100, nullable = false)
    private String lastModifyAccount;

    /**
     * 最後修改時間
     *
     * @since 1.0.0
     */
    @Column(name = "last_modify_date", nullable = false)
    private LocalDateTime lastModifyDate;

    /**
     * 使用者權限
     *
     * @see UserRole
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private UserRole userRoleByRoleId;

    /**
     * 最後修改者
     *
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modify_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByLastModifyAccount;

    /**
     * 取得使用者權限名稱
     *
     * @return 使用者權限名稱
     * @see UserRoleEnum#name
     * @since 1.0.0
     */
    public String getRoleName() {
        return roleId.name;
    }

    /**
     * 取得使用者性別文字，如：男、女
     *
     * @return 使用者性別文字
     * @see Gender#name
     * @since 1.0.0
     */
    public String getGenderName() {
        return gender.name;
    }

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    public Boolean isEnable() {
        return enable;
    }

    @Override
    public String getId() {
        return account;
    }

    @Override
    public Boolean getSave() {
        return save;
    }

    @Override
    public void setSave(Boolean isSave) {
        this.save = isSave;
    }
}
