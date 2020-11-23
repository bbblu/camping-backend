package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(name = "退貨原因")
public class RentalRecordBeReturnDescriptionBean {
    @Schema(description = "退貨原因")
    @NotBlank(message = "未填寫退貨原因")
    private String description;
}
