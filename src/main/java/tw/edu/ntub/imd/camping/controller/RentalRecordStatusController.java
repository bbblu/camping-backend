package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.camping.bean.RentalRecordBeReturnDescriptionBean;
import tw.edu.ntub.imd.camping.bean.RentalRecordCheckLogBean;
import tw.edu.ntub.imd.camping.bean.RentalRecordProductBrokenBean;
import tw.edu.ntub.imd.camping.bean.RentalRecordStatusChangeBean;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.dto.CreditCard;
import tw.edu.ntub.imd.camping.service.RentalRecordService;
import tw.edu.ntub.imd.camping.util.http.BindingResultUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Tag(name = "Rental", description = "租借紀錄")
@Controller
@RequestMapping(path = "/rental/{id}/status")
public class RentalRecordStatusController {
    private final RentalRecordService rentalRecordService;

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "同意租借",
            description = "同意買方租借此商品",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "已同意租借"
            )
    )
    @PatchMapping(path = "/agree")
    public ResponseEntity<String> agree(@PathVariable @Positive(message = "id - 應大於0") int id) {
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.NOT_PAY)
                        .changeDescription("已同意租借")
                        .build()
        );
        return createResponse("已同意租借", RentalRecordStatus.NOT_PAY);
    }

    private ResponseEntity<String> createResponse(String message, RentalRecordStatus newStatus) {
        ObjectData data = new ObjectData();
        data.add("ordinal", newStatus.ordinal());
        data.add("name", newStatus.toString());
        return ResponseEntityBuilder.success(message)
                .data(data)
                .build();
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "拒絕租借",
            description = "拒絕買方租借此商品",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "已拒絕租借"
            )
    )
    @PatchMapping(path = "/denied")
    public ResponseEntity<String> denied(@PathVariable @Positive(message = "id - 應大於0") int id) {
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.ALREADY_CANCEL)
                        .changeDescription("已拒絕租借")
                        .build()
        );
        return createResponse("已拒絕租借", RentalRecordStatus.ALREADY_CANCEL);
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "取消租借",
            description = "取消買方租借此商品",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "已取消租借"
            )
    )
    @PatchMapping(path = "/cancel")
    public ResponseEntity<String> cancel(@PathVariable @Positive(message = "id - 應大於0") int id) {
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.ALREADY_CANCEL)
                        .changeDescription("已取消租借")
                        .build()
        );
        return createResponse("取消成功", RentalRecordStatus.ALREADY_CANCEL);
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "付款",
            description = "買方付款",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "付款成功"
            )
    )
    @PatchMapping(path = "/payment")
    public ResponseEntity<String> payment(
            @PathVariable @Positive(message = "id - 應大於0") int id,
            @Valid @RequestBody CreditCard creditCard,
            BindingResult bindingResult
    ) {
        BindingResultUtils.validate(bindingResult);
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.NOT_PLACE)
                        .changeDescription("已付款")
                        .payload(creditCard)
                        .build()
        );
        return createResponse("付款成功", RentalRecordStatus.NOT_PLACE);
    }

    @Operation(
            tags = "Rental",
            method = "POST",
            summary = "新增商品檢查紀錄",
            description = "新增商品檢查紀錄",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "紀錄成功"
            )
    )
    @PostMapping(path = "/check-log")
    public ResponseEntity<String> createCheckLog(
            @PathVariable @Positive(message = "id - 應大於0") int id,
            @Valid RentalRecordCheckLogBean checkLog,
            BindingResult bindingResult
    ) {
        BindingResultUtils.validate(bindingResult);
        rentalRecordService.saveProductStatus(id, checkLog);
        return ResponseEntityBuilder.buildSuccessMessage("紀錄成功");
    }

    @Operation(
            tags = "Rental",
            method = "GET",
            summary = "查詢商品檢查紀錄",
            description = "查詢商品檢查紀錄",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功"
            )
    )
    @GetMapping(path = "/check-log")
    public ResponseEntity<String> searchCheckLog(@PathVariable @Positive(message = "id - 應大於0") int id) {
        return ResponseEntityBuilder.success("查詢成功")
                .data(rentalRecordService.searchCheckLog(id), (data, log) -> {
                    data.add("id", log.getId());
                    data.add("recordStatus", log.getRecordStatus().toString());
                    data.add("content", log.getContent());
                    data.addStringArray("imageArray", log.getImageUrlList());
                })
                .build();
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "退貨",
            description = "管理方退貨",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "已退貨"
            )
    )
    @PatchMapping(path = "/be-returned")
    @PreAuthorize("hasAnyAuthority('Administrator', 'Manager')")
    public ResponseEntity<String> beReturned(
            @PathVariable @Positive(message = "id - 應大於0") int id,
            @Valid @RequestBody RentalRecordBeReturnDescriptionBean beReturnDescription,
            BindingResult bindingResult
    ) {
        BindingResultUtils.validate(bindingResult);
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.BE_RETURNED)
                        .changeDescription(beReturnDescription.getDescription())
                        .build()
        );
        return createResponse("退貨成功", RentalRecordStatus.BE_RETURNED);
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "出貨",
            description = "賣方將商品送到倉庫並紀錄商品狀況",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "紀錄完成"
            )
    )
    @PatchMapping(path = "/placed")
    @PreAuthorize("hasAnyAuthority('Administrator', 'Manager')")
    public ResponseEntity<String> placed(
            @PathVariable @Positive(message = "id - 應大於0") int id
    ) {
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.NOT_PICK_UP)
                        .changeDescription("已送達倉庫")
                        .build()
        );
        return createResponse("紀錄完成", RentalRecordStatus.NOT_PICK_UP);
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "買方取貨",
            description = "買方取貨",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "取貨完成"
            )
    )
    @PatchMapping(path = "/pick-up")
    public ResponseEntity<String> pickUp(
            @PathVariable @Positive(message = "id - 應大於0") int id
    ) {
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.NOT_RETURN)
                        .changeDescription("取貨完成")
                        .build()
        );
        return createResponse("取貨完成", RentalRecordStatus.NOT_RETURN);
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "買方退貨",
            description = "買方退貨",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "退貨完成"
            )
    )
    @PatchMapping(path = "/terminate")
    public ResponseEntity<String> terminate(
            @PathVariable @Positive(message = "id - 應大於0") int id,
            @Valid @RequestBody RentalRecordBeReturnDescriptionBean beReturnDescription,
            BindingResult bindingResult
    ) {
        BindingResultUtils.validate(bindingResult);
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.TERMINATE)
                        .changeDescription(beReturnDescription.getDescription())
                        .payload(beReturnDescription)
                        .build()
        );
        return createResponse("退貨完成", RentalRecordStatus.TERMINATE);
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "買方退貨後賣方已領回",
            description = "買方退貨後賣方已領回",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "領回完成"
            )
    )
    @PatchMapping(path = "/already-terminate")
    public ResponseEntity<String> alreadyTerminate(
            @PathVariable @Positive(message = "id - 應大於0") int id
    ) {
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.ALREADY_TERMINATED)
                        .changeDescription("領回完成")
                        .build()
        );
        return createResponse("領回完成", RentalRecordStatus.ALREADY_TERMINATED);
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "買方賠償",
            description = "買方賠償",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "紀錄完成"
            )
    )
    @PatchMapping(path = "/compensate")
    public ResponseEntity<String> compensate(
            @PathVariable @Positive(message = "id - 應大於0") int id,
            @Valid @RequestBody RentalRecordProductBrokenBean productBroken,
            BindingResult bindingResult
    ) {
        BindingResultUtils.validate(bindingResult);
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.NOT_RETRIEVE)
                        .changeDescription("買方毀損商品")
                        .payload(productBroken)
                        .build()
        );
        return createResponse("紀錄完成", RentalRecordStatus.NOT_RETRIEVE);
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "歸還商品",
            description = "歸還商品",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "歸還完成"
            )
    )
    @PatchMapping(path = "/return")
    public ResponseEntity<String> returnProduct(
            @PathVariable @Positive(message = "id - 應大於0") int id
    ) {
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.NOT_RETRIEVE)
                        .changeDescription("買方歸還商品")
                        .build()
        );
        return createResponse("歸還完成", RentalRecordStatus.NOT_RETRIEVE);
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "取回商品",
            description = "取回商品",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "取回完成"
            )
    )
    @PatchMapping(path = "/retrieve")
    public ResponseEntity<String> retrieve(
            @PathVariable @Positive(message = "id - 應大於0") int id
    ) {
        rentalRecordService.updateStatus(
                RentalRecordStatusChangeBean.builder()
                        .id(id)
                        .newStatus(RentalRecordStatus.NOT_COMMENT)
                        .changeDescription("賣方已取回商品")
                        .build()
        );
        return createResponse("取回完成", RentalRecordStatus.NOT_COMMENT);
    }
}
