package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(name = "修改密碼", description = "修改密碼")
@Data
public class UpdatePasswordBean {
    @Schema(description = "舊密碼", example = "hello")
    @NotBlank(message = "舊密碼 - 未填寫")
    private String password;

    @Schema(description = "新密碼", example = "goodbye")
    @NotBlank(message = "新密碼 - 未填寫")
    private String newPassword;
}
