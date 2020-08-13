package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(name = "產品類型", description = "產品類型")
@Data
@EqualsAndHashCode
public class ProductTypeBean {
    @Schema(description = "類型編號", example = "1", minimum = "1", maximum = "10")
    private Integer id;
    @Schema(description = "類型", example = "睡帳")
    private String name;
}
