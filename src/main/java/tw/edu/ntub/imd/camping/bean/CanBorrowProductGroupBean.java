package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Schema(name = "可租借商品群組", description = "可租借商品群組")
@Data
@EqualsAndHashCode
public class CanBorrowProductGroupBean {
    @Schema(description = "群組編號", minimum = "1", example = "1")
    private Integer id;

    @Schema(description = "封面圖網址", example = "https://www.ntub.edu.tw/var/file/0/1000/img/1595/logo.png")
    private String coverImage;

    @Schema(description = "價格", minimum = "0", example = "6999")
    private Integer price;

    @Schema(description = "可租借起始日期", type = "string", example = "2020/08/14 00:00")
    private LocalDateTime borrowStartDate;

    @Schema(description = "可租借結束日期", type = "string", example = "2020/08/21 19:30")
    private LocalDateTime borrowEndDate;

    @Schema(description = "城市", example = "台北市 中正區")
    private String city;

    @Hidden
    private String cityName;

    @Hidden
    private String cityAreaName;

    @Schema(description = "出租者帳號(暱稱)", example = "admin(管理員)")
    private String userName;
}
