package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.converter.BooleanTo1And0Converter;
import tw.edu.ntub.imd.camping.databaseconfig.converter.GenderConverter;
import tw.edu.ntub.imd.camping.databaseconfig.converter.UserRoleEnumConverter;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Experience;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Gender;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserRoleEnum;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 使用者
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = {"userRoleByRoleId", "userByLastModifyAccount"})
@Entity
@Table(name = "user", schema = Config.DATABASE_NAME)
public class User {
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
    @Convert(converter = UserRoleEnumConverter.class)
    @Column(name = "role_id", nullable = false, columnDefinition = "UNSIGNED")
    private UserRoleEnum roleId = UserRoleEnum.USER;

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    @Getter(AccessLevel.NONE)
    @Convert(converter = BooleanTo1And0Converter.class)
    @Column(name = "enable", nullable = false)
    private Boolean enable = true;

    /**
     * 露營經驗(0: 新手/ 1: 有過幾次經驗)
     *
     * @see Experience
     * @since 1.0.0
     */
    private Experience experience = Experience.ROOKIE;

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
     * 使用者性別(0: 男/ 1: 女/ 2: 未提供)
     *
     * @see Gender
     * @since 1.0.0
     */
    @Convert(converter = GenderConverter.class)
    @Column(name = "gender", length = 1, nullable = false)
    private Gender gender;

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
    private LocalDateTime createDate = LocalDateTime.now();

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
    private LocalDateTime lastModifyDate = LocalDateTime.now();

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
     * 取得使用者的姓氏 + 名稱
     *
     * @return 使用者全名
     * @see #getFullName(String)
     * @since 1.0.0
     */
    public String getFullName() {
        return getFullName(null);
    }

    /**
     * 取得使用者的姓氏 + 分隔文字 + 名稱
     *
     * @param delimiter 分隔文字，可以是{@code null}
     * @return 使用者全名
     * @see #getFullName()
     * @since 1.0.0
     */
    public String getFullName(@Nullable String delimiter) {
        return (lastName != null ? lastName : "") + (delimiter != null ? delimiter : "") + (firstName != null ? firstName : "");
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
     * 取得最後修改者的姓氏 + 名稱
     *
     * @return 最後修改者全名
     * @see #getLastModifyUserName()
     * @see #getFullName()
     * @since 1.0.0
     */
    public String getLastModifyUserName() {
        return getLastModifyUserName(null);
    }

    /**
     * 取得最後修改者的姓氏 + 分隔文字 + 名稱
     *
     * @param delimiter 分隔文字，可以是{@code null}
     * @return 最後修改者全名
     * @see #getLastModifyUserName()
     * @see #getFullName(String)
     * @since 1.0.0
     */
    public String getLastModifyUserName(@Nullable String delimiter) {
        return userByLastModifyAccount.getFullName(delimiter);
    }

    public Boolean isEnable() {
        return enable;
    }
}
