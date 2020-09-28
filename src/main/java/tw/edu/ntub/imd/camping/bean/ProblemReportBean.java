package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportStatus;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportType;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Schema(name = "問題回報", description = "問題回報")
@Data
public class ProblemReportBean {
    @Hidden
    @Null(message = "id - 不得填寫")
    private Integer id;

    @Schema(description = "問題類型(0: 帳號相關/ 1: 租借相關/ 2: 款項相關/ 3: 其他)", type = "int", example = "0")
    @NotNull(message = "問題類型 - 未填寫")
    private ProblemReportType type;

    @Hidden
    @Null(message = "status - 不得填寫")
    private ProblemReportStatus status;

    @Schema(description = "回報信箱", example = "10646000@ntub.edu.tw")
    @NotBlank(message = "回報信箱 - 未填寫")
    @Size(max = 255, message = "回報信箱 - 輸入字數大於{max}個字")
    @Email(message = "回報信箱 - 格式不符合信箱格式")
    private String reporterEmail;

    @Hidden
    @Null(message = "reportDate - 不得填寫")
    private LocalDateTime reportDate;

    @Schema(description = "問題內容", example = "帳號怎麼都無法註冊成功")
    @NotBlank(message = "問題內容 - 未填寫")
    private String reportContent;

    @Hidden
    @Null(message = "handler - 不得填寫")
    private String handler;

    @Hidden
    @Null(message = "handleResult - 不得填寫")
    private String handleResult;

    @Hidden
    @Null(message = "modifyId - 不得填寫")
    private String modifyId;

    @Hidden
    @Null(message = "modifyDate - 不得填寫")
    private LocalDateTime modifyDate;
}