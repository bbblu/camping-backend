package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.camping.bean.ProblemReportBean;
import tw.edu.ntub.imd.camping.service.ProblemReportService;
import tw.edu.ntub.imd.camping.util.http.BindingResultUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;

import javax.validation.Valid;

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
}
