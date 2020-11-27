package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "商品子類型", description = "商品子類型")
@Data
public class ProductSubTypeBean {
    @Schema(description = "子類型編號", minimum = "1", example = "1")
    private Integer id;
    @Schema(description = "類型編號", minimum = "1", example = "1")
    private Integer type;
    @Schema(description = "子類型名稱", example = "兩人登山帳")
    private String name;
    @Schema(description = "商品類型")
    private ProductTypeBean productType;
}
