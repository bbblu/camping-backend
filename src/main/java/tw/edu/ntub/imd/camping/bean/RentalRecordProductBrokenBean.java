package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(name = "租借商品毀損紀錄")
public class RentalRecordProductBrokenBean {
    @Schema(description = "毀損紀錄", example = "帳篷破洞")
    @NotBlank(message = "未填寫毀損紀錄")
    private String description;
}
