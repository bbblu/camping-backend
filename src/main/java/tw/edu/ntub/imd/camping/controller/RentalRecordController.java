package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.camping.bean.RentalRecordBean;
import tw.edu.ntub.imd.camping.service.RentalRecordService;
import tw.edu.ntub.imd.camping.util.http.BindingResultUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.object.SingleValueObjectData;

import javax.validation.Valid;

@Tag(name = "Rental", description = "商品租借API")
@RestController
@RequestMapping(path = "/rental")
public class RentalRecordController {
    private final RentalRecordService rentalRecordService;

    public RentalRecordController(RentalRecordService rentalRecordService) {
        this.rentalRecordService = rentalRecordService;
    }

    @Operation(
            tags = "Rental",
            method = "POST",
            summary = "租借商品",
            description = "租借商品",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "租借成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Id.class)
                    )
            )
    )
    @PostMapping(path = "")
    public ResponseEntity<String> create(@RequestBody @Valid RentalRecordBean rentalRecordBean, BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        RentalRecordBean saveResult = rentalRecordService.save(rentalRecordBean);
        return ResponseEntityBuilder.success()
                .message("租借成功")
                .data(SingleValueObjectData.create("id", saveResult.getId()))
                .build();
    }

    // |---------------------------------------------------------------------------------------------------------------------------------------------|
    // |---------------------------------------------------------以下為Swagger所需使用的Schema---------------------------------------------------------|
    // |---------------------------------------------------------------------------------------------------------------------------------------------|

    @Schema(name = "租借編號", description = "租借商品時的回傳資料")
    @Data
    private static class Id {
        @Schema(description = "租借紀錄編號", example = "1")
        private int id;
    }
}
