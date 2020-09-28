package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(name = "商品圖", description = "商品圖")
@Data
@EqualsAndHashCode
public class ProductImageBean {
    @Hidden
    private Integer id;

    @Hidden
    private Integer productId;

    @Schema(description = "圖片連結", example = "https://www.ntub.edu.tw/var/file/0/1000/img/1595/logo.png")
    private String url;
}
