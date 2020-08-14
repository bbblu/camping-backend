package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.birc.common.wrapper.date.DateTimePattern;
import tw.edu.ntub.imd.camping.bean.CanBorrowProductGroupBean;
import tw.edu.ntub.imd.camping.bean.ProductGroupBean;
import tw.edu.ntub.imd.camping.bean.ProductGroupFilterDataBean;
import tw.edu.ntub.imd.camping.bean.ProductTypeBean;
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
import java.util.List;
import java.util.Map;

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
}
