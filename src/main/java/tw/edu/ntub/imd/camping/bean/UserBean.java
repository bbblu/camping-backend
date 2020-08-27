package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Experience;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Gender;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserRoleEnum;
import tw.edu.ntub.imd.camping.validation.CreateUser;

import javax.annotation.Nullable;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(name = "使用者", description = "使用者")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBean {
    @Schema(description = "帳號", example = "test")
    @NotBlank(groups = CreateUser.class, message = "帳號 - 未填寫")
    @Size(max = 100, message = "帳號 - 輸入字數大於{max}個字")
    private String account;

    @Schema(description = "密碼", example = "hello")
    @NotBlank(groups = CreateUser.class, message = "密碼 - 未填寫")
    private String password;

    @Hidden
    private UserRoleEnum roleId;

    @Hidden
    @Getter(AccessLevel.NONE)
    private Boolean enable;

    @Schema(description = "露營經驗(0: 新手/ 1: 有過幾次經驗)", type = "int", example = "0")
    @NotNull(groups = CreateUser.class, message = "露營經驗 - 未填寫")
    private Experience experience;

    @Schema(description = "姓氏", example = "王")
    @NotBlank(groups = CreateUser.class, message = "姓氏 - 未填寫")
    @Size(max = 50, message = "姓氏 - 輸入字數大於{max}個字")
    private String lastName;

    @Schema(description = "名字", example = "小明")
    @NotBlank(groups = CreateUser.class, message = "名字 - 未填寫")
    @Size(max = 50, message = "名字 - 輸入字數大於{max}個字")
    private String firstName;

    @Schema(description = "暱稱", example = "煞氣a小明")
    @NotBlank(groups = CreateUser.class, message = "暱稱 - 未填寫")
    @Size(max = 50, message = "暱稱 - 輸入字數大於{max}個字")
    private String nickName;

    @Schema(description = "性別(0: 男/ 1: 女/ 2: 未提供)", type = "int", example = "0")
    @NotNull(groups = CreateUser.class, message = "性別 - 未填寫")
    private Gender gender;

    @Schema(description = "信箱", example = "10646000@ntub.edu.tw")
    @NotBlank(groups = CreateUser.class, message = "信箱 - 未填寫")
    @Size(max = 255, message = "信箱 - 輸入字數大於{max}個字")
    @Email(message = "信箱 - 格式不符合信箱格式")
    private String email;

    @Schema(description = "地址", example = "台北市中正區濟南路321號")
    @NotBlank(groups = CreateUser.class, message = "地址 - 未填寫")
    @Size(max = 50, message = "地址 - 輸入字數大於{max}個字")
    private String address;

    @Schema(description = "生日", type = "string($date)", example = "2020/01/01")
    @NotNull(groups = CreateUser.class, message = "生日 - 未填寫")
    @Past(message = "生日 - 應為過去日期")
    private LocalDate birthday;

    @Hidden
    private LocalDateTime createDate;

    @Hidden
    private String lastModifyAccount;

    @Hidden
    private LocalDateTime lastModifyDate;

    @Hidden
    public String getRoleName() {
        return roleId.name;
    }

    @Hidden
    public String getFullName() {
        return getFullName(null);
    }

    @Hidden
    public String getFullName(@Nullable String delimiter) {
        return (lastName != null ? lastName : "") + (delimiter != null ? delimiter : "") + (firstName != null ? firstName : "");
    }

    @Hidden
    public String getGenderName() {
        return gender.name;
    }

    @Hidden
    public Boolean isEnable() {
        return enable;
    }
}
