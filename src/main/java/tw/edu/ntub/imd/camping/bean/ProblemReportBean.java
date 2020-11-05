package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.edu.ntub.birc.common.annotation.AliasName;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportStatus;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportType;
import tw.edu.ntub.imd.camping.validation.CreateProblemReport;
import tw.edu.ntub.imd.camping.validation.HandleProblemReport;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Schema(name = "問題回報", description = "問題回報")
@Data
public class ProblemReportBean {
    @Hidden
    @Null(groups = {
            CreateProblemReport.class,
            HandleProblemReport.class
    }, message = "id - 不得填寫")
    private Integer id;

    @Schema(description = "問題類型", example = "1")
    @NotNull(message = "問題類型 - 未填寫")
    @PositiveOrZero(message = "問題類型 - 應為大於等於0的正數")
    private ProblemReportType type;

    @Schema(description = "狀態(0: 未處理/ 1: 處理中/ 2: 已完成)", example = "1")
    @Null(groups = {
            CreateProblemReport.class,
            HandleProblemReport.class
    }, message = "status - 不得填寫")
    private ProblemReportStatus status;

    @Schema(description = "回報者信箱", example = "10646007@ntub.edu.tw")
    @NotBlank(message = "回報者信箱 - 未填寫")
    @Email(message = "回報者信箱 - 格式不符合信箱格式")
    private String reporterEmail;

    @Schema(description = "回報時間", example = "2020/11/03 20:54:33")
    @Null(groups = {
            CreateProblemReport.class,
            HandleProblemReport.class
    }, message = "reportDate - 不得填寫")
    private LocalDateTime reportDate;

    @Schema(description = "回報內容", example = "無法上架商品")
    @NotBlank(groups = {
            CreateProblemReport.class,
            HandleProblemReport.class
    }, message = "回報內容 - 未填寫")
    private String reportContent;

    @Schema(description = "處理人", example = "admin")
    @Null(groups = {
            CreateProblemReport.class,
            HandleProblemReport.class
    }, message = "handler - 不得填寫")
    private String handler;

    @Hidden
    @Null(groups = {
            CreateProblemReport.class,
            HandleProblemReport.class
    }, message = "handleDate - 不得填寫")
    private LocalDateTime handleDate;

    @Schema(description = "處理結果", example = "已回報系統工程師，非常感謝您的回覆")
    @NotBlank(groups = HandleProblemReport.class, message = "處理結果 - 未填寫")
    private String handleResult;

    @Hidden
    @Null(groups = {
            CreateProblemReport.class,
            HandleProblemReport.class
    }, message = "modifyId - 不得填寫")
    private String lastModifyAccount;

    @Hidden
    @Null(groups = {
            CreateProblemReport.class,
            HandleProblemReport.class
    }, message = "modifyDate - 不得填寫")
    private LocalDateTime lastModifyDate;

    @Hidden
    @AliasName("userByHandler")
    private UserBean handlerAsUser;
}
