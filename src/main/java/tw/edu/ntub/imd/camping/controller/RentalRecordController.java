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
import tw.edu.ntub.birc.common.util.MathUtils;
import tw.edu.ntub.birc.common.wrapper.date.DateTimePattern;
import tw.edu.ntub.imd.camping.bean.*;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.exception.form.InvalidFormException;
import tw.edu.ntub.imd.camping.service.RentalRecordService;
import tw.edu.ntub.imd.camping.util.http.BindingResultUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.object.CollectionObjectData;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;
import tw.edu.ntub.imd.camping.util.json.object.SingleValueObjectData;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Tag(name = "Rental", description = "商品租借API")
@RestController
@RequestMapping(path = "/rental")
public class RentalRecordController {
    private final RentalRecordService rentalRecordService;

    @GetMapping(path = "/status")
    public ResponseEntity<String> searchStatus() {
        return ResponseEntityBuilder.success("查詢成功")
                .data(List.of(RentalRecordStatus.values()), (data, status) -> {
                    data.add("id", status.ordinal());
                    data.add("name", status.toString());
                })
                .build();
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
                        this::addRenterRentalRecordToData
                )
                .build();
    }

    private void addRenterRentalRecordToData(ObjectData rentalRecordData, RentalRecordBean rentalRecord) {
        addRentalRecordToData(rentalRecordData, rentalRecord);
        ProductGroupBean productGroup = rentalRecord.getProductGroup();
        UserBean createUser = productGroup.getCreateUser();
        ObjectData sellerData = rentalRecordData.addObject("user");
        sellerData.add("account", createUser.getAccount());
        sellerData.add("nickName", createUser.getNickName());
        sellerData.add("email", createUser.getEmail());
        sellerData.add("cellPhone", createUser.getCellPhone());
    }

    private void addRentalRecordToData(ObjectData rentalRecordData, RentalRecordBean rentalRecord) {
        ProductGroupBean productGroup = rentalRecord.getProductGroup();
        rentalRecordData.add("id", rentalRecord.getId());
        rentalRecordData.add("status", rentalRecord.getStatus().ordinal());
        rentalRecordData.add("productGroupId", rentalRecord.getProductGroupId());
        rentalRecordData.add("borrowStartDate", rentalRecord.getBorrowStartDate(), DateTimePattern.DEFAULT_DATE);
        rentalRecordData.add("borrowEndDate", rentalRecord.getBorrowEndDate(), DateTimePattern.DEFAULT_DATE);
        rentalRecordData.add("name", productGroup.getName());
        rentalRecordData.add("coverImage", productGroup.getCoverImage());
        CityBean city = productGroup.getCity();
        rentalRecordData.add("areaName", city.getAreaName());
        rentalRecordData.add("price", productGroup.getPrice());
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
                        this::addProductOwnerRentalRecordToData
                )
                .build();
    }

    private void addProductOwnerRentalRecordToData(ObjectData rentalRecordData, RentalRecordBean rentalRecord) {
        addRentalRecordToData(rentalRecordData, rentalRecord);
        UserBean renter = rentalRecord.getRenter();
        ObjectData sellerData = rentalRecordData.addObject("user");
        sellerData.add("account", renter.getAccount());
        sellerData.add("nickName", renter.getNickName());
        sellerData.add("email", renter.getEmail());
        sellerData.add("cellPhone", renter.getCellPhone());
    }

    @Operation(
            tags = "Rental",
            method = "GET",
            summary = "查詢狀態變更的原因",
            description = "查詢狀態變更的原因",
            parameters = {
                    @Parameter(name = "id", description = "紀錄編號", required = true, example = "1"),
                    @Parameter(name = "status", description = "變更後的狀態", required = true, example = "1"),
            }
    )
    @GetMapping(path = "/{id}/{status}/change-description")
    public ResponseEntity<String> getStatusChangeLog(
            @PathVariable @Positive(message = "id - 應大於0") int id,
            @PathVariable int status
    ) {
        SingleValueObjectData data = SingleValueObjectData.create(
                "description",
                rentalRecordService.getChangeLogDescription(id, RentalRecordStatus.values()[status])
        );
        return ResponseEntityBuilder.success().message("查詢成功").data(data).build();
    }

    @Operation(
            tags = "Rental",
            method = "POST",
            summary = "評價交易",
            description = "評價交易，若登入者為此交易的買方，則會評價借方，反之亦然，若雙方都評價完成，會修改租借紀錄狀態至未評價",
            parameters = @Parameter(name = "id", description = "交易編號", example = "1")
    )
    @PostMapping(path = "/{id}/comment")
    public ResponseEntity<String> comment(
            @PathVariable @Positive(message = "id - 應大於0") int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserCommentSchema.class)
                    )
            ) @RequestBody String requestBodyJsonString
    ) {
        ObjectData requestBody = new ObjectData(requestBodyJsonString);
        Integer comment = requestBody.getInt("comment");
        if (comment == null) {
            throw new InvalidFormException("評價分數 - 未填寫");
        } else if (MathUtils.isNotInRange(comment, 0, 5)) {
            throw new InvalidFormException("評價分數 - 應介於0 ~ 5之間");
        } else {
            rentalRecordService.saveComment(id, comment);
            return ResponseEntityBuilder.buildSuccessMessage("評價成功");
        }
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
        @Schema(description = "賣方(若查詢租借紀錄)、買方(若查詢出租紀錄)")
        private User user;
        @Schema(description = "賣方聯絡方式", example = "LineId : 1234")
        private String contact;
        @Schema(description = "租借日期", example = "2020/08/28 15:03")
        private String rentalDate;
        @ArraySchema(minItems = 1, uniqueItems = true, schema = @Schema(implementation = Detail.class))
        private Detail[] detailArray;

        @Schema(name = "查詢租借紀錄 - 賣方", description = "查詢租借紀錄的回傳資料中的user")
        @Data
        private static class User {
            @Schema(description = "暱稱", example = "煞氣a小明")
            private String nickName;
            @Schema(description = "信箱", example = "10646007@ntub.edu.tw")
            private String email;
            @Schema(description = "手機", example = "0912345678")
            private String cellPhone;
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

    @Schema(name = "使用者評價資料", description = "使用者評價資料")
    @Data
    private static class UserCommentSchema {
        @Schema(description = "評價分數", minimum = "1", maximum = "5")
        private Byte comment;
    }
}
