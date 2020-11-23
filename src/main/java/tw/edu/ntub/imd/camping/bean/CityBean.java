package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(name = "城市及區域", description = "城市代表台北市、新北市，區域代表中正區、板橋區")
@Data
@EqualsAndHashCode
public class CityBean {
    @Schema(description = "編號", example = "1")
    private Integer id;
    @Schema(description = "城市名稱", example = "台北市")
    private String name;
    @Schema(description = "區域名稱", example = "中正區")
    private String areaName;
}
