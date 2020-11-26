package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.ProblemReportBean;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.exception.form.InvalidFormException;
import tw.edu.ntub.imd.camping.service.ProblemReportService;
import tw.edu.ntub.imd.camping.util.http.BindingResultUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;
import tw.edu.ntub.imd.camping.validation.CreateProblemReport;
import tw.edu.ntub.imd.camping.validation.HandleProblemReport;

import javax.validation.constraints.*;

@AllArgsConstructor
@Tag(name = "ProblemReport", description = "問題回報")
@RestController
@RequestMapping(path = "/problem-report")
public class ProblemReportController {
    private final ProblemReportService problemReportService;

    @Operation(
            tags = "ProblemReport",
            method = "POST",
            summary = "新增問題回報",
            description = "新增問題回報",
            responses = @ApiResponse(
                    description = "新增成功",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json"
                    )
            )
    )
    @PostMapping(path = "")
    public ResponseEntity<String> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "新增問題回報的內容",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateProblemReportSchema.class)
                    )
            ) @RequestBody @Validated(CreateProblemReport.class) ProblemReportBean problemReportBean,
            BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        problemReportService.save(problemReportBean);
        return ResponseEntityBuilder.buildSuccessMessage("回報完成");
    }

    @Operation(
            tags = "ProblemReport",
            method = "PATCH",
            summary = "問題回報接單",
            description = "問題回報接單",
            parameters = @Parameter(name = "id", description = "問題回報編號", required = true, example = "1"),
            responses = @ApiResponse(
                    description = "接單完成",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json"
                    )
            )
    )
    @PreAuthorize("hasAnyAuthority('Administrator', 'Manager')")
    @PatchMapping(path = "/{id}/handle")
    public ResponseEntity<String> handle(@PathVariable @Positive(message = "id應為正數") int id) {
        problemReportService.updateHandler(id);
        return ResponseEntityBuilder.buildSuccessMessage("接單完成");
    }

    @Operation(
            tags = "ProblemReport",
            method = "PATCH",
            summary = "問題回報處理結果",
            description = "問題回報處理結果",
            parameters = @Parameter(name = "id", description = "問題回報編號", required = true, example = "1"),
            responses = @ApiResponse(
                    description = "處理結果送出成功",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json"
                    )
            )
    )
    @PreAuthorize("hasAnyAuthority('Administrator', 'Manager')")
    @PatchMapping(path = "/{id}/handle/result")
    public ResponseEntity<String> handleResult(
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "處理結果",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HandleResultSchema.class)
                    )
            ) @RequestBody String requestBodyJsonString) {
        ObjectData requestBody = new ObjectData(requestBodyJsonString);
        String handleResult = requestBody.getString("handleResult");
        if (StringUtils.isBlank(handleResult)) {
            throw new InvalidFormException("處理結果 - 未填寫");
        } else {
            problemReportService.updateHandleResult(id, handleResult);
        }
        return ResponseEntityBuilder.buildSuccessMessage("處理結果送出成功");
    }

    @GetMapping(path = "")
    public ResponseEntity<String> search() {
        return ResponseEntityBuilder.success("查詢成功")
                .data(problemReportService.searchAll(), (data, problemReportBean) -> {
                    data.add("id", problemReportBean.getId());
                    data.add("type", problemReportBean.getType().ordinal());
                    data.add("status", problemReportBean.getStatus().ordinal());
                    data.add("reporterEmail", problemReportBean.getReporterEmail());
                    data.add("reportContent", problemReportBean.getReportContent());
                    data.add("reportDate", problemReportBean.getReportDate());
                    if (problemReportBean.getHandlerAsUser() != null) {
                        UserBean handlerAsUser = problemReportBean.getHandlerAsUser();
                        data.add("handler", handlerAsUser.getFullName());
                    }
                    data.add("handleDate", problemReportBean.getHandleDate());
                    data.add("handleResult", problemReportBean.getHandleResult());
                })
                .build();
    }

    @Schema(name = "建立問題回報", description = "建立問題回報")
    @Data
    private static class CreateProblemReportSchema {
        @Schema(description = "問題類型(0: 帳號相關/ 1: 租借相關/ 2: 款項相關/ 3: 其他)", example = "1")
        @NotNull(message = "問題類型 - 未填寫")
        @PositiveOrZero(message = "問題類型 - 應為大於等於0的正數")
        private int type;

        @Schema(description = "回報者信箱", example = "10646007@ntub.edu.tw")
        @NotBlank(message = "回報者信箱 - 未填寫")
        @Email(message = "回報者信箱 - 格式不符合信箱格式")
        private String reporterEmail;

        @Schema(description = "回報主題", example = "上架商品")
        @NotBlank(message = "回報主題 - 未填寫")
        @Size(max = 50, message = "回報主題 - 輸入字數大於{max}個字")
        private String reportTitle;

        @Schema(description = "回報內容", example = "無法上架商品")
        @NotBlank(groups = {
                CreateProblemReport.class,
                HandleProblemReport.class
        }, message = "回報內容 - 未填寫")
        private String reportContent;
    }

    @Schema(name = "問題回報處理結果", description = "問題回報處理結果")
    @Data
    private static class HandleResultSchema {
        @Schema(description = "處理結果", required = true, example = "已回報系統工程師，非常感謝您的回覆")
        private String handleResult;
    }
}
