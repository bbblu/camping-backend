package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(name = "商品", description = "商品")
@Data
@EqualsAndHashCode
public class ProductBean {
    @Hidden
    private Integer Id;

    @Hidden
    private Integer groupId;

    @Schema(description = "商品類型", example = "1")
    @NotNull(message = "商品類型 - 未填寫")
    @Positive(message = "商品類型 - 應為大於0的正數")
    private Integer type;

    @Schema(description = "商品名稱", example = "OO牌帳篷")
    @NotBlank(message = "商品名稱 - 未填寫")
    @Size(max = 200, message = "商品名稱 - 輸入字數大於{max}個字")
    private String name;

    @Schema(description = "數量", example = "2")
    @NotNull(message = "數量 - 未填寫")
    @Positive(message = "數量 - 應為大於0的正數")
    private Integer count;

    @Schema(description = "品牌", example = "OO")
    @NotBlank(message = "品牌 - 未填寫")
    @Size(max = 100, message = "品牌 - 輸入字數大於{max}個字")
    private String brand;

    @Schema(description = "使用方式", example = "內附搭帳篷說明書")
    @NotBlank(message = "使用方式 - 未填寫")
    private String useInformation;

    @Schema(description = "損壞賠償", example = "缺少零件：1/$200、布劃破：$1000")
    @NotBlank(message = "損壞賠償 - 未填寫")
    @Size(max = 100, message = "損壞賠償 - 輸入字數大於{max}個字")
    private String brokenCompensation;

    @Schema(description = "備註", example = "附有教學影片，若在搭設過程有疑問，都可以聯絡我")
    private String memo;

    @Schema(description = "商品圖片")
    private List<@Valid ProductImageBean> imageArray;

    private List<
            @NotBlank(message = "相關連結 - 不能包含未填寫字串")
            @Size(max = 600, message = "相關連結 - 有連結的字數超過{max}個字")
            @URL(message = "相關連結 - 有聯結的格式不符合網址連結的格式")
                    String> relatedLinkArray;

    @Hidden
    private List<ProductRelatedLinkBean> relatedLinkList;
}
