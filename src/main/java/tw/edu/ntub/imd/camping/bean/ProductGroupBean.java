package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.imd.camping.validation.CreateProductGroup;
import tw.edu.ntub.imd.camping.validation.UpdateProductGroup;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "商品群組", description = "商品群組")
@Data
@EqualsAndHashCode
public class ProductGroupBean {
    @Hidden
    @Null(groups = {CreateProductGroup.class, UpdateProductGroup.class}, message = "編號 - 不得填寫")
    private Integer id;

    @Schema(description = "商品群組名稱", example = "便宜帳篷、桌椅三件套，限時特價$3990")
    @NotBlank(groups = CreateProductGroup.class, message = "商品群組名稱 - 未填寫")
    @Size(groups = {CreateProductGroup.class, UpdateProductGroup.class}, max = 300, message = "商品群組名稱 - 輸入字數大於{max}個字")
    private String name;

    @Schema(description = "封面圖連結", example = "https://www.ntub.edu.tw/var/file/0/1000/img/1595/logo.png")
    private String coverImage;

    @Schema(description = "城市編號", example = "1")
    @NotNull(groups = CreateProductGroup.class, message = "城市編號 - 未填寫")
    private Integer cityId;

    @Schema(description = "價格", example = "3990")
    @NotNull(groups = CreateProductGroup.class, message = "價格 - 未填寫")
    @PositiveOrZero(groups = {CreateProductGroup.class, UpdateProductGroup.class}, message = "價格 - 應為大於等於0的數字")
    private Integer price;

    @Schema(description = "租借起始日期(需在結束日期之前)", example = "2020/08/11")
    @NotNull(groups = CreateProductGroup.class, message = "租借起始日期 - 未選擇")
    @FutureOrPresent(groups = {CreateProductGroup.class, UpdateProductGroup.class}, message = "租借起始日期 - 應選擇現在或未來時間")
    private LocalDateTime borrowStartDate;

    @Schema(description = "租借結束日期(需在起始日期之後)", example = "2020/08/12")
    @NotNull(groups = CreateProductGroup.class, message = "租借結束日期 - 未選擇")
    @FutureOrPresent(groups = {CreateProductGroup.class, UpdateProductGroup.class}, message = "租借結束日期 - 應選擇現在或未來時間")
    private LocalDateTime borrowEndDate;

    @Schema(description = "商品")
    @Size(groups = CreateProductGroup.class, min = 1, message = "商品 - 至少需新增一項")
    private List<@Valid ProductBean> productArray;

    @Hidden
    @Null(groups = {CreateProductGroup.class, UpdateProductGroup.class}, message = "createUser - 不得填寫")
    private UserBean createUser;

    @Hidden
    @Null(groups = {CreateProductGroup.class, UpdateProductGroup.class}, message = "comment - 不得填寫")
    private Double comment;

    @Hidden
    @Null(groups = {CreateProductGroup.class, UpdateProductGroup.class}, message = "city - 不得填寫")
    private CityBean city;

    @Hidden
    @AssertTrue(groups = {CreateProductGroup.class, UpdateProductGroup.class}, message = "租借起始日期應等於結束日期或在結束日期之前")
    private boolean isBorrowStartDateAfterOrEqualEndDate() {
        if (borrowStartDate != null && borrowEndDate != null) {
            return borrowStartDate.isEqual(borrowEndDate) || borrowStartDate.isBefore(borrowEndDate);
        } else {
            return true;
        }
    }

    @Hidden
    @AssertTrue(groups = {CreateProductGroup.class, UpdateProductGroup.class}, message = "租借日期範圍應有十四日間隔")
    private boolean isBorrowDateRangeLargeThen6Days() {
        if (borrowStartDate != null && borrowEndDate != null) {
            return Duration.between(borrowStartDate, borrowEndDate).toDays() >= 14;
        } else {
            return true;
        }
    }
}
