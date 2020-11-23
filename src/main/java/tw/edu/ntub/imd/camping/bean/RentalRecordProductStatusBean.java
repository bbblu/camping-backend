package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Schema(name = "商品狀況")
public class RentalRecordProductStatusBean {
    @Schema(description = "商品狀況描述", example = "與上架資訊一致")
    @NotBlank(message = "未填寫商品狀況")
    private String description;
}
