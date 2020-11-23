package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.edu.ntub.imd.camping.validation.CreateUser;

import javax.validation.constraints.*;

@Schema(name = "忘記密碼", description = "忘記密碼所需資料")
@Data
public class ForgotPasswordBean {
    @Schema(description = "帳號", example = "admin")
    @NotBlank(message = "帳號 - 未填寫")
    @Size(max = 100, message = "帳號 - 輸入字數大於{max}個字")
    private String account;

    @Schema(description = "信箱", example = "10646007@ntub.edu.tw")
    @NotBlank(message = "信箱 - 未填寫")
    @Email(message = "信箱 - 格式不符")
    private String email;

    @Schema(description = "手機號碼", example = "admin")
    @NotNull(groups = CreateUser.class, message = "手機號碼 - 未填寫")
    @Pattern(regexp = "^09[0-9]{8}$", message = "手機號碼 - 格式不符")
    private String cellPhone;
}
