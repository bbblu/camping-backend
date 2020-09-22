package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.imd.camping.validation.CreateProductGroup;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "商品群組", description = "商品群組")
@Data
@EqualsAndHashCode
public class ProductGroupBean {
    @Hidden
    @Null(groups = CreateProductGroup.class, message = "編號 - 不得填寫")
    private Integer id;

    @Schema(description = "匯款帳戶", minLength = 10, maxLength = 16, example = "12345671234567")
    @NotBlank(groups = CreateProductGroup.class, message = "匯款帳戶 - 未填寫")
    @Pattern(regexp = "^[0-9]{10,16}$", message = "匯款帳戶 - 應為10到16個字")
    private String bankAccount;

    @Schema(description = "商品群組名稱", example = "便宜帳篷、桌椅三件套，限時特價$3990")
    @NotBlank(groups = CreateProductGroup.class, message = "商品群組名稱 - 未填寫")
    @Size(max = 300, message = "商品群組名稱 - 輸入字數大於{max}個字")
    private String name;

    @Schema(description = "封面圖連結", example = "https://www.ntub.edu.tw/var/file/0/1000/img/1595/logo.png")
    private String coverImage;

    @Schema(description = "城市名稱，如臺北市、宜蘭縣", example = "臺北市")
    @NotBlank(groups = CreateProductGroup.class, message = "城市名稱 - 未填寫")
    @Size(max = 20, message = "城市名稱 - 輸入字數大於{max}個字")
    private String cityName;

    @Schema(description = "區名稱，如中正區、宜蘭市", example = "中正區")
    @NotBlank(groups = CreateProductGroup.class, message = "區名稱 - 未填寫")
    @Size(max = 20, message = "區名稱 - 輸入字數大於{max}個字")
    private String cityAreaName;

    @Schema(description = "價格", example = "3990")
    @NotNull(groups = CreateProductGroup.class, message = "價格 - 未填寫")
    @PositiveOrZero(message = "價格 - 應為大於等於0的數字")
    private Integer price;

    @Schema(description = "租借起始日期(需在結束日期之前)", example = "2020/08/11")
    @NotNull(groups = CreateProductGroup.class, message = "租借起始日期 - 未選擇")
    @FutureOrPresent(message = "租借起始日期 - 應選擇現在或未來時間")
    private LocalDateTime borrowStartDate;

    @Schema(description = "租借結束日期(需在起始日期之後)", example = "2020/08/12")
    @NotNull(groups = CreateProductGroup.class, message = "租借結束日期 - 未選擇")
    @FutureOrPresent(message = "租借結束日期 - 應選擇現在或未來時間")
    private LocalDateTime borrowEndDate;

    @Schema(description = "商品")
    @Size(groups = CreateProductGroup.class, min = 1, message = "商品 - 至少需新增一項")
    private List<@Valid ProductBean> productArray;

    @Hidden
    @Null(message = "createUser - 不得填寫")
    private UserBean createUser;

    @Hidden
    @AssertTrue(message = "租借起始日期應等於結束日期或在結束日期之前")
    private boolean isBorrowStartDateAfterOrEqualEndDate() {
        if (borrowStartDate != null && borrowEndDate != null) {
            return borrowStartDate.isEqual(borrowEndDate) || borrowStartDate.isBefore(borrowEndDate);
        } else {
            return true;
        }
    }
}
