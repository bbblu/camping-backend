package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "商品品牌", description = "商品品牌")
@Data
public class ProductBrandBean {
    @Schema(description = "品牌編號", minimum = "1", example = "1")
    private Integer id;
    @Schema(description = "品牌名稱", example = "天天租")
    private String name;
}
