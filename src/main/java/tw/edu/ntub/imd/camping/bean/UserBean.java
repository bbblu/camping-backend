package tw.edu.ntub.imd.camping.bean;

import lombok.*;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Experience;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Gender;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserRoleEnum;

import javax.annotation.Nullable;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBean {
    @NotBlank(message = "帳號 - 未填寫")
    @Size(max = 100, message = "帳號 - 輸入字數大於{max}個字")
    private String account;

    @NotBlank(message = "密碼 - 未填寫")
    private String password;

    private UserRoleEnum roleId;

    @Getter(AccessLevel.NONE)
    private Boolean enable;

    @NotNull(message = "經驗 - 未填寫")
    private Experience experience;

    @NotBlank(message = "姓氏 - 未填寫")
    @Size(max = 50, message = "姓氏 - 輸入字數大於{max}個字")
    private String lastName;

    @NotBlank(message = "名字 - 未填寫")
    @Size(max = 50, message = "名字 - 輸入字數大於{max}個字")
    private String firstName;

    @NotBlank(message = "暱稱 - 未填寫")
    @Size(max = 50, message = "暱稱 - 輸入字數大於{max}個字")
    private String nickName;

    @NotNull(message = "性別 - 未填寫")
    private Gender gender;

    @NotBlank(message = "信箱 - 未填寫")
    @Size(max = 255, message = "信箱 - 輸入字數大於{max}個字")
    @Email(message = "信箱 - 格式不符合信箱格式")
    private String email;

    @NotBlank(message = "地址 - 未填寫")
    @Size(max = 50, message = "地址 - 輸入字數大於{max}個字")
    private String address;
    @NotNull(message = "生日 - 未填寫")
    @Past(message = "生日 - 應為過去日期")
    private LocalDate birthday;
    private LocalDateTime createDate;
    private String lastModifyAccount;
    private LocalDateTime lastModifyDate;

    public String getRoleName() {
        return roleId.name;
    }

    public String getFullName() {
        return getFullName(null);
    }

    public String getFullName(@Nullable String delimiter) {
        return (lastName != null ? lastName : "") + (delimiter != null ? delimiter : "") + (firstName != null ? firstName : "");
    }

    public String getGenderName() {
        return gender.name;
    }

    public Boolean isEnable() {
        return enable;
    }
}
