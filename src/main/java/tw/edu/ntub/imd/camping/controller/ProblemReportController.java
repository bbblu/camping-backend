package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.camping.bean.ProblemReportBean;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportType;
import tw.edu.ntub.imd.camping.service.ProblemReportService;
import tw.edu.ntub.imd.camping.util.http.BindingResultUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

import javax.validation.Valid;
import java.util.Arrays;

@Tag(name = "Problem Report", description = "問題回報API")
@RestController
@RequestMapping("/problem-report")
public class ProblemReportController {
    private final ProblemReportService problemReportService;

    @Autowired
    public ProblemReportController(ProblemReportService problemReportService) {
        this.problemReportService = problemReportService;
    }

    @Operation(
            tags = "Problem Report",
            method = "POST",
            summary = "新增問題回報",
            description = "新增問題回報",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "新增成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    )
    @PostMapping("")
    public ResponseEntity<String> create(
            @Valid @RequestBody ProblemReportBean problemReportBean,
            BindingResult bindingResult
    ) {
        BindingResultUtils.validate(bindingResult);
        problemReportService.save(problemReportBean);
        return ResponseEntityBuilder.success().message("新增成功").build();
    }

    @Operation(
            tags = "Problem Report",
            method = "GET",
            summary = "查詢問題類型列表",
            description = "查詢問題類型列表",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProblemReportController.ProblemReportTypeSchema.class))
                    )
            )
    )
    @GetMapping(path = "/type")
    public ResponseEntity<String> getExperienceList() {
        return ResponseEntityBuilder.success("查詢成功")
                .data(Arrays.asList(ProblemReportType.values()), this::addProblemReportTypeToObjectData)
                .build();
    }

    private void addProblemReportTypeToObjectData(ObjectData data, ProblemReportType problemReportType) {
        data.add("id", problemReportType.id);
        data.add("display", problemReportType.toString());
    }

    // |---------------------------------------------------------------------------------------------------------------------------------------------|
    // |----------------------------------------------------------以下為Swagger所需使用的Schema---------------------------------------------------------|
    // |---------------------------------------------------------------------------------------------------------------------------------------------|
    @Schema(name = "問題類型", description = "問題類型")
    @Data
    private static class ProblemReportTypeSchema {
        @Schema(description = "編號", example = "0")
        private String id;

        @Schema(description = "顯示文字", example = "帳號相關")
        private String display;
    }
}
