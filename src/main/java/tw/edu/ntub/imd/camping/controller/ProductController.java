package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.camping.bean.ProductGroupBean;
import tw.edu.ntub.imd.camping.service.ProductService;
import tw.edu.ntub.imd.camping.util.http.BindingResultUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Tag(name = "Product", description = "商品相關API")
@RestController
@RequestMapping(path = "/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
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
        productService.save(productGroup);
        return ResponseEntityBuilder.success().message("上架成功").build();
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
        productService.delete(id);
        return ResponseEntityBuilder.success().message("刪除成功").build();
    }

    @DeleteMapping(path = "/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "productId") @Positive(message = "編號 - 應為大於0的數字") Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntityBuilder.success().message("刪除成功").build();
    }
}
