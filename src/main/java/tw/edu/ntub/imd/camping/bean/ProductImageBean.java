package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;
import tw.edu.ntub.birc.common.util.BooleanUtils;
import tw.edu.ntub.birc.common.util.StringUtils;

import javax.validation.constraints.AssertTrue;

@Schema(name = "商品圖", description = "商品圖")
@Data
@EqualsAndHashCode
public class ProductImageBean {
    @Hidden
    private Integer id;

    @Hidden
    private Integer productId;

    @Schema(description = "圖片連結", example = "https://www.ntub.edu.tw/var/file/0/1000/img/1595/logo.png")
    @URL(message = "圖片連結 - 請輸入網址連結")
    private String url;

    @Schema(description = "圖片檔", type = "file")
    private MultipartFile imageFile;

    @Hidden
    @AssertTrue(message = "請上傳圖片檔或圖片連結")
    private boolean isUploadImage() {
        return (imageFile != null && BooleanUtils.isFalse(imageFile.isEmpty())) ||
                StringUtils.isNotBlank(url);
    }

    @Hidden
    @AssertTrue(message = "圖片檔與圖片連結請擇一上傳")
    private boolean isOnlyUploadImageOrOnlySendImageUrl() {
        return (imageFile != null && BooleanUtils.isFalse(imageFile.isEmpty()) && StringUtils.isBlank(url)) ||
                ((imageFile == null || imageFile.isEmpty()) && StringUtils.isNotBlank(url));
    }
}
