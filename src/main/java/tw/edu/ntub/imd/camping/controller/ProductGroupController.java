package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.birc.common.wrapper.date.DateTimePattern;
import tw.edu.ntub.imd.camping.bean.*;
import tw.edu.ntub.imd.camping.service.CityService;
import tw.edu.ntub.imd.camping.service.ProductGroupService;
import tw.edu.ntub.imd.camping.util.http.BindingResultUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.array.ArrayData;
import tw.edu.ntub.imd.camping.util.json.object.CollectionObjectData;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Product", description = "商品相關API")
@RestController
@RequestMapping(path = "/product-group")
public class ProductGroupController {
    private final DecimalFormat priceFormat = new DecimalFormat("$ #,###");
    private final ProductGroupService productGroupService;
    private final CityService cityService;

    public ProductGroupController(ProductGroupService productGroupService, CityService cityService) {
        this.productGroupService = productGroupService;
        this.cityService = cityService;
    }

    @Operation(
            tags = "Product",
            method = "POST",
            summary = "上架商品群組",
            description = "上架商品群組，因有檔案上傳，需使用Form-Data呼叫API",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = ProductGroupBean.class)
                    )
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "上架成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    )
    @PostMapping(path = "")
    public ResponseEntity<String> create(@Valid ProductGroupBean productGroup, BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        productGroupService.save(productGroup);
        return ResponseEntityBuilder.success().message("上架成功").build();
    }

    @Operation(
            tags = "Product",
            method = "GET",
            summary = "查詢商品類型",
            description = "查詢商品類型",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProductTypeBean.class))
                    )
            )
    )
    @GetMapping(path = "/product/type")
    public ResponseEntity<String> searchProductType() {
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(productGroupService.searchAllProductType(), this::addProductTypeToObjectData)
                .build();
    }

    private void addProductTypeToObjectData(ObjectData data, ProductTypeBean type) {
        data.add("id", type.getId());
        data.add("name", type.getName());
    }

    @Operation(
            tags = "Product",
            method = "GET",
            summary = "查詢商品群組列表過濾器資料",
            description = "查詢過濾器所需的地點、商品種類",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductGroupListFilterSchema.class)
                    )
            )
    )
    @GetMapping(path = "/filter")
    public ResponseEntity<String> searchProductGroupFilter() {
        List<ProductTypeBean> typeList = productGroupService.searchAllProductType();
        ObjectData data = new ObjectData();
        CollectionObjectData collectionData = data.createCollectionData();
        collectionData.add("type", typeList, this::addProductTypeToObjectData);
        addCityToObjectData(cityService.searchAllEnableCity(), data);
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(data)
                .build();
    }

    private void addCityToObjectData(Map<String, List<String>> cityMap, ObjectData data) {
        ObjectData cityData = data.addObject("city");
        ArrayData cityNameArray = cityData.addArray("nameArray");
        ArrayData cityAreaNameArray = cityData.addArray("areaNameArray");
        cityMap.forEach((name, areaNameList) -> {
            cityNameArray.add(name);
            cityAreaNameArray.addStringArray(areaNameList);
        });
    }

    @Operation(
            tags = "Product",
            method = "GET",
            summary = "查詢商品群組列表",
            description = "查詢商品群組列表",
            parameters = {
                    @Parameter(name = "borrowStartDate", description = "可租借起始時間", example = "2020/08/14"),
                    @Parameter(name = "borrowEndDate", description = "可租借結束時間", example = "2020/08/20"),
                    @Parameter(name = "cityAreaName", description = "城市區域名稱", example = "中正區"),
                    @Parameter(
                            name = "typeArray",
                            description = "商品類型陣列",
                            example = "[1, 2, 3]"
                    ),
                    @Parameter(name = "priceRange", description = "價格範圍(0: 0 ~ 2,000/ 1: 2,001 ~ 4,000)", example = "0")
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CanBorrowProductGroupBean.class)
                    )
            )
    )
    @GetMapping(path = "")
    public ResponseEntity<String> searchCanBorrowProductGroup(ProductGroupFilterDataBean filterData) {
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(productGroupService.searchCanBorrowProductGroup(filterData), (data, canBorrowProductGroup) -> {
                    data.add("id", canBorrowProductGroup.getId());
                    data.add("coverImage", canBorrowProductGroup.getCoverImage());
                    data.add("price", priceFormat.format(canBorrowProductGroup.getPrice()));
                    data.add("borrowStartDate", canBorrowProductGroup.getBorrowStartDate(), DateTimePattern.of("yyyy/MM/dd HH:mm"));
                    data.add("borrowEndDate", canBorrowProductGroup.getBorrowEndDate(), DateTimePattern.of("yyyy/MM/dd HH:mm"));
                    data.add("city", canBorrowProductGroup.getCity());
                    data.add("userName", canBorrowProductGroup.getUserName());
                })
                .build();
    }

    @Operation(
            tags = "Product",
            method = "GET",
            summary = "查詢商品群組",
            description = "查詢商品群組",
            parameters = @Parameter(name = "id", description = "商品群組編號", example = "1"),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductGroupContentSchema.class)
                    )
            )
    )
    @GetMapping(path = "/{id}")
    public ResponseEntity<String> getGroupContent(
            @PathVariable(name = "id")
            @Positive(message = "群組編號不能為負數")
                    int id
    ) {
        Optional<ProductGroupBean> optionalGroup = productGroupService.getById(id);
        if (optionalGroup.isPresent()) {
            ProductGroupBean productGroupBean = optionalGroup.get();
            ObjectData data = new ObjectData();
            data.add("name", productGroupBean.getName());
            data.add("city", String.format("%s %s", productGroupBean.getCityName(), productGroupBean.getCityAreaName()));
            data.add("price", priceFormat.format(productGroupBean.getPrice()));
            data.add("borrowDateRange", String.format(
                    "%s ~ %s",
                    productGroupBean.getBorrowStartDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                    productGroupBean.getBorrowEndDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
            ));
            ContactInformationBean contactInformation = productGroupBean.getContactInformation();
            data.add("contactInformation", contactInformation.getContent());

            CollectionObjectData collectionData = data.createCollectionData();
            collectionData.add("productArray", productGroupBean.getProductArray(), (productData, productBean) -> {
                productData.add("id", productBean.getId());
                productData.add("type", productBean.getTypeName());
                productData.add("count", productBean.getCount());
                productData.add("brand", productBean.getBrand());
                productData.add("useInformation", productBean.getUseInformation());
                productData.add("brokenCompensation", productBean.getBrokenCompensation());
                productData.add("memo", productBean.getMemo());
                if (CollectionUtils.isNotEmpty(productBean.getImageArray())) {
                    productData.addStringArray("imageArray", productBean.getImageArray()
                            .stream()
                            .map(ProductImageBean::getUrl)
                            .collect(Collectors.toList())
                    );
                } else {
                    productData.addStringArray("imageArray", new String[0]);
                }
                if (CollectionUtils.isNotEmpty(productBean.getRelatedLinkList())) {
                    productData.addStringArray("relatedLinkArray", productBean.getRelatedLinkList()
                            .stream()
                            .map(ProductRelatedLinkBean::getUrl)
                            .collect(Collectors.toList())
                    );
                } else {
                    productData.addStringArray("relatedLinkArray", new String[0]);
                }
            });
            return ResponseEntityBuilder.success()
                    .message("查詢成功")
                    .data(data)
                    .build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            tags = "Product",
            method = "DELETE",
            summary = "刪除商品群組",
            description = "刪除商品群組",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "刪除成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    )
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") @Positive(message = "編號 - 應為大於0的數字") Integer id) {
        productGroupService.delete(id);
        return ResponseEntityBuilder.success().message("刪除成功").build();
    }

    @Operation(
            tags = "Product",
            method = "DELETE",
            summary = "刪除商品",
            description = "刪除商品",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "刪除成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    )
    @DeleteMapping(path = "/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "productId") @Positive(message = "編號 - 應為大於0的數字") Integer productId) {
        productGroupService.deleteProduct(productId);
        return ResponseEntityBuilder.success().message("刪除成功").build();
    }

    @Operation(
            tags = "Product",
            method = "DELETE",
            summary = "刪除商品圖片",
            description = "刪除商品圖片",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "刪除成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    )
    @DeleteMapping(path = "/product/image/{imageId}")
    public ResponseEntity<String> deleteProductImage(@PathVariable(name = "imageId") @Positive(message = "編號 - 應為大於0的數字") Integer imageId) {
        productGroupService.deleteProductImage(imageId);
        return ResponseEntityBuilder.success().message("刪除成功").build();
    }

    @Operation(
            tags = "Product",
            method = "DELETE",
            summary = "刪除商品相關連結",
            description = "刪除商品相關連結",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "刪除成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    )
    @DeleteMapping(path = "/product/related-link/{relatedLinkId}")
    public ResponseEntity<String> deleteProductRelatedLink(@PathVariable(name = "relatedLinkId") @Positive(message = "編號 - 應為大於0的數字") Integer relatedLinkId) {
        productGroupService.deleteProductRelatedLink(relatedLinkId);
        return ResponseEntityBuilder.success().message("刪除成功").build();
    }

    @Schema(name = "商品群組列表過濾器資料", description = "商品群組列表過濾器資料")
    @Data
    private static class ProductGroupListFilterSchema {
        @ArraySchema(minItems = 1, maxItems = 10, uniqueItems = true, schema = @Schema(description = "商品類型"))
        private ProductTypeBean[] type;
        @Schema(description = "地點")
        private CityController.SearchCitySchema city;
    }

    @Schema(name = "商品群組內容", description = "商品群組內容")
    @Data
    private static class ProductGroupContentSchema {
        @Schema(description = "商品群組名稱", example = "便宜帳篷、桌椅三件套，限時特價$3990")
        private String name;
        @Schema(description = "城市", example = "台北市 中正區")
        private String city;
        @Schema(description = "租借價格", minimum = "0", example = "$ 3,990")
        private String price;
        @Schema(description = "可租借時間範圍", example = "2020/08/14 15:00 ~ 2020/08/20 00:00")
        private String borrowDateRange;
        @Schema(description = "聯絡方式", example = "Email: 10646007@ntub.edu.tw")
        private String contactInformation;
        @ArraySchema(minItems = 0, uniqueItems = true, schema = @Schema(description = "商品陣列"))
        private ProductContentSchema productArray;

        @Hidden
        @Data
        private static class ProductContentSchema {
            @Schema(description = "商品編號", minimum = "1", example = "6")
            private Integer id;
            @Schema(description = "商品類型", example = "睡帳")
            private String type;
            @Schema(description = "商品數量", example = "2")
            private Integer count;
            @Schema(description = "商品品牌", example = "OO")
            private String brand;
            @Schema(description = "使用說明", example = "內附搭帳棚說明書")
            private String useInformation;
            @Schema(description = "損壞賠償", example = "缺少零件：1/$200、布劃破：$1000")
            private String brokenCompensation;
            @Schema(description = "備註", nullable = true, example = "附有教學影片，若在搭設過程有疑問，都可以聯絡我")
            private String memo;
            @ArraySchema(minItems = 0, schema = @Schema(description = "商品圖片陣列", example = "https://www.ntub.edu.tw/var/file/0/1000/img/1595/logo.png"))
            private String[] imageArray;
            @ArraySchema(minItems = 0, schema = @Schema(description = "商品相關連結陣列", example = "https://www.fooish.com/jquery/"))
            private String[] relatedLinkArray;
        }
    }
}
