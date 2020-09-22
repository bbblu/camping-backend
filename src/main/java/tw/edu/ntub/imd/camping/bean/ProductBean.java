package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;
import tw.edu.ntub.birc.common.annotation.AliasName;
import tw.edu.ntub.imd.camping.validation.CreateProduct;
import tw.edu.ntub.imd.camping.validation.CreateProductGroup;

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
    private Integer id;

    @Hidden
    private Integer groupId;

    @Schema(description = "商品類型", example = "1")
    @NotNull(groups = {CreateProductGroup.class, CreateProduct.class}, message = "商品類型 - 未填寫")
    @Positive(groups = {CreateProductGroup.class, CreateProduct.class}, message = "商品類型 - 應為大於0的正數")
    private Integer type;

    @Hidden
    @AliasName("productTypeByType.name")
    private String typeName;

    @Schema(description = "商品名稱", example = "快搭客廳炊事帳")
    @NotBlank(groups = {CreateProductGroup.class, CreateProduct.class}, message = "商品名稱 - 未填寫")
    @Size(groups = {CreateProductGroup.class, CreateProduct.class}, max = 200, message = "商品名稱 - 輸入字數大於{max}個字")
    private String name;

    @Schema(description = "數量", example = "1")
    @NotNull(groups = {CreateProductGroup.class, CreateProduct.class}, message = "數量 - 未填寫")
    @Positive(groups = {CreateProductGroup.class, CreateProduct.class}, message = "數量 - 應為大於0的正數")
    private Integer count;

    @Schema(description = "品牌", example = "雜牌")
    @NotBlank(groups = {CreateProductGroup.class, CreateProduct.class}, message = "品牌 - 未填寫")
    @Size(groups = {CreateProductGroup.class, CreateProduct.class}, max = 100, message = "品牌 - 輸入字數大於{max}個字")
    private String brand;

    @Schema(description = "外觀狀況", example = "300cm*300cm*250cm(高)")
    @NotBlank(groups = {CreateProductGroup.class, CreateProduct.class}, message = "外觀狀況 - 未填寫")
    @Size(groups = {CreateProductGroup.class, CreateProduct.class}, max = 500, message = "外觀狀況 - 輸入字數大於{max}個字")
    private String appearance;

    @Schema(description = "使用方式", example = "四人同時向外拉，並往上推，小心不要夾到手，若遇下雨，必須曬乾再收起來。")
    @NotBlank(groups = {CreateProductGroup.class, CreateProduct.class}, message = "使用方式 - 未填寫")
    private String useInformation;

    @Schema(description = "損壞賠償", example = "缺少零件：1/$200、布劃破：$1000")
    @NotBlank(groups = {CreateProductGroup.class, CreateProduct.class}, message = "損壞賠償 - 未填寫")
    @Size(groups = {CreateProductGroup.class, CreateProduct.class}, max = 100, message = "損壞賠償 - 輸入字數大於{max}個字")
    private String brokenCompensation;

    @Schema(description = "相關連結")
    @Size(groups = {CreateProductGroup.class, CreateProduct.class}, max = 600, message = "相關連結 - 有連結的字數超過{max}個字")
    @URL(groups = {CreateProductGroup.class, CreateProduct.class}, message = "相關連結 - 有連結的格式不符合網址連結的格式")
    private String relatedLink;

    @Schema(description = "備註", example = "附有教學影片，若在搭設過程有疑問，都可以聯絡我")
    private String memo;

    @Schema(description = "商品圖片")
    @Size(groups = {CreateProductGroup.class, CreateProduct.class}, max = 5, message = "商品圖 - 最多{max}張")
    private List<@Valid ProductImageBean> imageArray;
}
