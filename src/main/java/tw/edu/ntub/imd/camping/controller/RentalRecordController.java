package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.birc.common.wrapper.date.DateTimePattern;
import tw.edu.ntub.imd.camping.bean.*;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.service.RentalRecordService;
import tw.edu.ntub.imd.camping.util.http.BindingResultUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.object.CollectionObjectData;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;
import tw.edu.ntub.imd.camping.util.json.object.SingleValueObjectData;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Tag(name = "Rental", description = "商品租借API")
@RestController
@RequestMapping(path = "/rental")
public class RentalRecordController {
    private static final DecimalFormat PRICE_FORMATTER = new DecimalFormat("$ #,###");
    private final RentalRecordService rentalRecordService;

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
        return ResponseEntityBuilder.success("租借成功")
                .data(SingleValueObjectData.create("id", saveResult.getId()))
                .build();
    }

    @Operation(
            tags = "Rental",
            method = "GET",
            summary = "查詢租借紀錄",
            description = "查詢登入者的所有租借紀錄",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SearchRentalRecordResult.class)
                    )
            )
    )
    @GetMapping(path = "")
    public ResponseEntity<String> searchAll() {
        return ResponseEntityBuilder.success("查詢成功")
                .data(
                        rentalRecordService.searchByRenterAccount(SecurityUtils.getLoginUserAccount()),
                        this::addRentalRecordToData
                )
                .build();
    }

    private void addRentalRecordToData(ObjectData rentalRecordData, RentalRecordBean rentalRecord) {
        ProductGroupBean productGroup = rentalRecord.getProductGroup();
        rentalRecordData.add("id", rentalRecord.getId());
        rentalRecordData.add("status", rentalRecord.getStatus().ordinal());
        rentalRecordData.add("borrowStartDate", rentalRecord.getBorrowStartDate(), DateTimePattern.DEFAULT_DATE);
        rentalRecordData.add("borrowEndDate", rentalRecord.getBorrowEndDate(), DateTimePattern.DEFAULT_DATE);
        rentalRecordData.add("borrowRange", String.format(
                "%s-%s",
                rentalRecord.getBorrowStartDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                rentalRecord.getBorrowEndDate().format(DateTimeFormatter.ofPattern("MM/dd"))
        ));
        rentalRecordData.add("name", productGroup.getName());
        rentalRecordData.add("coverImage", productGroup.getCoverImage());
        rentalRecordData.add("areaName", productGroup.getCityAreaName());
        rentalRecordData.add("price", PRICE_FORMATTER.format(productGroup.getPrice()));
        UserBean createUser = productGroup.getCreateUser();
        ObjectData sellerData = rentalRecordData.addObject("seller");
        sellerData.add("nickName", createUser.getNickName());
        sellerData.add("email", createUser.getEmail());
        rentalRecordData.add("rentalDate", rentalRecord.getRentalDate(), DateTimePattern.of("yyyy/MM/dd HH:mm"));

        CollectionObjectData collectionObjectData = rentalRecordData.createCollectionData();
        collectionObjectData.add("detailArray", rentalRecord.getDetailBeanList(), (detailData, detail) -> {
            ProductBean product = detail.getProduct();
            detailData.add("status", detail.getStatus().ordinal());
            detailData.add("type", product.getTypeName());
            detailData.add("name", product.getName());
            detailData.add("count", product.getCount());
            detailData.add("brand", product.getBrand());
            detailData.add("useInformation", product.getUseInformation());
            detailData.add("brokenCompensation", product.getBrokenCompensation());
            detailData.add("relatedLink", product.getRelatedLink());
            detailData.addStringArray("imageArray", product.getImageArray() != null ?
                    product.getImageArray().stream().map(ProductImageBean::getUrl).collect(Collectors.toList()) :
                    Collections.emptyList()
            );
        });
    }

    @Operation(
            tags = "Rental",
            method = "GET",
            summary = "查詢出借紀錄",
            description = "查詢登入者的所有出借紀錄",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SearchRentalRecordResult.class)
                    )
            )
    )
    @GetMapping(path = "/borrow")
    public ResponseEntity<String> searchAllBorrowRecord() {
        return ResponseEntityBuilder.success("查詢成功")
                .data(
                        rentalRecordService.searchByProductGroupCreateAccount(SecurityUtils.getLoginUserAccount()),
                        this::addRentalRecordToData
                )
                .build();
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "更新租借紀錄狀態",
            description = "更新租借紀錄狀態至下一階段",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "更新成功"
            )
    )
    @PatchMapping(path = "/{id}/status/next")
    public ResponseEntity<String> updateStatusToNext(@PathVariable @Positive(message = "id - 應大於0") int id) {
        rentalRecordService.updateStatusToNext(id);
        return ResponseEntityBuilder.buildSuccessMessage("更新成功");
    }

    @Operation(
            tags = "Rental",
            method = "POST",
            summary = "取消租借紀錄",
            description = "請求對方取消租借",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1")
    )
    @PostMapping(path = "/{id}/cancel")
    public ResponseEntity<String> requestCancelRecord(
            @PathVariable @Positive(message = "id - 應大於0") int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = CancelDetail.class))
            ) @RequestBody String requestBodyJsonString
    ) {
        ObjectData requestBody = new ObjectData(requestBodyJsonString);
        rentalRecordService.requestCancelRecord(id, requestBody.getString("cancelDetail"));
        return ResponseEntityBuilder.buildSuccessMessage("請求取消成功，等待對方回應");
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "拒絕取消租借紀錄",
            description = "拒絕取消租借",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1")
    )
    @PatchMapping(path = "/{id}/cancel/denied")
    public ResponseEntity<String> deniedCancel(
            @PathVariable @Positive(message = "id - 應大於0") int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = DeniedCancelDetail.class))
            ) @RequestBody String requestBodyJsonString) {
        ObjectData requestBody = new ObjectData(requestBodyJsonString);
        rentalRecordService.deniedCancel(id, requestBody.getString("deniedDetail"));
        return ResponseEntityBuilder.buildSuccessMessage("成功拒絕");
    }

    @Operation(
            tags = "Rental",
            method = "PATCH",
            summary = "同意取消租借紀錄",
            description = "同意取消租借",
            parameters = @Parameter(name = "id", description = "紀錄編號", required = true, example = "1")
    )
    @PatchMapping(path = "/{id}/cancel/agree")
    public ResponseEntity<String> agreeCancel(@PathVariable @Positive(message = "id - 應大於0") int id) {
        rentalRecordService.agreeCancel(id);
        return ResponseEntityBuilder.buildSuccessMessage("已同意對方的取消請求");
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

    @Schema(name = "查詢租借紀錄", description = "查詢租借紀錄時的回傳資料")
    @Data
    private static class SearchRentalRecordResult {
        @Schema(description = "編號", minimum = "1", example = "1")
        private Integer id;
        @Schema(description = "狀態(0:取消/ 1: 未取貨/ 2:未歸還/ 3:已歸還/ 4: 已檢查)", minimum = "0", maximum = "4", example = "4")
        private Integer status;
        @Schema(description = "租借期間", example = "2020/12/10-12/13")
        private String borrowRange;
        @Schema(description = "租借開始時間", example = "2020/12/10")
        private String borrowStartDate;
        @Schema(description = "租借結束時間", example = "2020/12/13")
        private String borrowEndDate;
        @Schema(description = "商品群組名稱", example = "4人帳四角睡帳客廳帳 桌椅 營燈 廚具")
        private String name;
        @Schema(description = "封面圖URL", example = "https://www.ntub.edu.tw/var/file/0/1000/img/1595/logo.png")
        private String coverImage;
        @Schema(description = "城市區域名稱", example = "中和區")
        private String areaName;
        @Schema(description = "租借價格", example = "$ 3,990")
        private String price;
        @Schema(description = "賣方")
        private User seller;
        @Schema(description = "賣方聯絡方式", example = "LineId : 1234")
        private String contact;
        @Schema(description = "租借日期", example = "2020/08/28 15:03")
        private String rentalDate;
        @ArraySchema(minItems = 1, uniqueItems = true, schema = @Schema(implementation = Detail.class))
        private Detail[] detailArray;

        @Schema(name = "查詢租借紀錄 - 賣方", description = "查詢租借紀錄的回傳資料中的seller")
        @Data
        private static class User {
            @Schema(description = "暱稱", example = "煞氣a小明")
            private String nickName;
            @Schema(description = "信箱", example = "10646007@ntub.edu.tw")
            private String email;
        }

        @Schema(name = "查詢租借紀錄 - 詳細內容", description = "查詢租借紀錄的回傳資料中的detailArray")
        @Data
        private static class Detail {
            @Schema(description = "狀態(0: 未歸還/ 1: 已歸還/ 2: 損壞/ 3: 遺失)", minimum = "0", maximum = "3", example = "2")
            private Integer status;
            @Schema(description = "商品類型", example = "客廳帳")
            private String type;
            @Schema(description = "商品名稱", example = "快搭客廳炊事帳")
            private String name;
            @Schema(description = "商品數量", example = "2")
            private Integer count;
            @Schema(description = "商品品牌", example = "無")
            private String brand;
            @Schema(description = "使用方式", example = "四人同時向外拉，並往上推，小心不要夾到手。若遇下雨，必須曬乾再收起來。")
            private String useInformation;
            @Schema(description = "損壞賠償", example = "損壞致無法使用，原價七成賠償。\n損壞布面，原價五成賠償\n損壞小部分但堪用，原價三成賠償。")
            private String brokenCompensation;
            @ArraySchema(minItems = 0, uniqueItems = true, schema = @Schema(example = "https://www.ntub.edu.tw/var/file/0/1000/img/1595/logo.png"))
            private String[] imageArray;
            @ArraySchema(minItems = 0, uniqueItems = true, schema = @Schema(example = "https://www.ntub.edu.tw"))
            private String[] relatedLinkArray;
        }
    }

    @Schema(name = "取消原因", description = "取消租借時的傳遞資料")
    @Data
    private static class CancelDetail {
        @Schema(description = "取消原因", example = "那天沒空")
        private String cancelDetail;
    }

    @Schema(name = "拒絕取消原因", description = "拒絕取消租借時的傳遞資料")
    @Data
    private static class DeniedCancelDetail {
        @Schema(description = "拒絕取消原因", example = "已出貨")
        private String deniedDetail;
    }
}
