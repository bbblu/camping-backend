package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema(name = "產品相關連結", description = "產品相關連結")
@Data
@EqualsAndHashCode
public class ProductRelatedLinkBean {
    @Hidden
    private Integer id;

    @Hidden
    private Integer productId;

    @Schema(description = "網址", example = "https://www.ntub.edu.tw/")
    @NotBlank(message = "網址 - 未填寫")
    @Size(max = 600, message = "網址 - 輸入字數大於{max}個字")
    @URL(message = "網址 - 請輸入網址連結")
    private String url;
}
