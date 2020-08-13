package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;
import tw.edu.ntub.birc.common.util.BooleanUtils;
import tw.edu.ntub.birc.common.util.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "商品群組", description = "商品群組")
@Data
@EqualsAndHashCode
public class ProductGroupBean {
    @Hidden
    private Integer id;

    @Schema(description = "商品群組名稱", example = "便宜帳篷、桌椅三件套，限時特價$3990")
    @NotBlank(message = "商品群組名稱 - 未填寫")
    @Size(max = 300, message = "商品群組名稱 - 輸入字數大於{max}個字")
    private String name;

    @Hidden
    private String coverImage;

    @Schema(description = "封面圖檔，與封面圖連結擇一上傳", type = "file")
    private MultipartFile coverImageFile;

    @Schema(description = "封面圖連結，與封面圖檔擇一上傳", example = "https://www.ntub.edu.tw/var/file/0/1000/img/1595/logo.png")
    @URL(message = "封面圖連結 - 請輸入網址連結")
    private String coverImageUrl;

    @Schema(description = "城市名稱，如臺北市、宜蘭縣", example = "臺北市")
    @NotBlank(message = "城市名稱 - 未填寫")
    @Size(max = 20, message = "城市名稱 - 輸入字數大於{max}個字")
    private String cityName;

    @Schema(description = "區名稱，如中正區、宜蘭市", example = "中正區")
    @NotBlank(message = "區名稱 - 未填寫")
    @Size(max = 20, message = "區名稱 - 輸入字數大於{max}個字")
    private String cityAreaName;

    @Schema(description = "價格", example = "3990")
    @NotNull(message = "價格 - 未填寫")
    @PositiveOrZero(message = "價格 - 應為大於等於0的數字")
    private Integer price;

    @Schema(description = "租借起始日期(需在結束日期之前)", example = "2020/08/11")
    @NotNull(message = "租借起始日期 - 未選擇")
    @FutureOrPresent(message = "租借起始日期 - 應選擇現在或未來時間")
    private LocalDateTime borrowStartDate;

    @Schema(description = "租借結束日期(需在起始日期之後)", example = "2020/08/12")
    @NotNull(message = "租借結束日期 - 未選擇")
    @FutureOrPresent(message = "租借結束日期 - 應選擇現在或未來時間")
    private LocalDateTime borrowEndDate;

    @Schema(description = "聯絡方式編號", example = "1")
    @Positive(message = "聯絡方式編號 - 應為大於0的數字")
    private Integer contactInformationId;

    @Hidden
    private ContactInformationBean contactInformation;

    @Schema(description = "商品")
    @Size(min = 1, message = "商品 - 至少需新增一項")
    private List<@Valid ProductBean> productArray;

    @Hidden
    @AssertTrue(message = "租借起始日期應等於結束日期或在結束日期之前")
    private boolean isBorrowStartDateAfterOrEqualEndDate() {
        if (borrowStartDate != null && borrowEndDate != null) {
            return borrowStartDate.isEqual(borrowEndDate) || borrowStartDate.isBefore(borrowEndDate);
        } else {
            return true;
        }
    }

    @Hidden
    @AssertTrue(message = "請上傳封面圖檔或封面圖連結")
    private boolean isUploadCoverImage() {
        return (coverImageFile != null && BooleanUtils.isFalse(coverImageFile.isEmpty())) ||
                StringUtils.isNotBlank(coverImageUrl);
    }

    @Hidden
    @AssertTrue(message = "封面圖檔與封面圖連結請擇一上傳")
    private boolean isOnlyUploadCoverImageOrOnlySendCoverImageUrl() {
        return (coverImageFile != null && BooleanUtils.isFalse(coverImageFile.isEmpty()) && StringUtils.isBlank(coverImageUrl)) ||
                ((coverImageFile == null || coverImageFile.isEmpty()) && StringUtils.isNotBlank(coverImageUrl));
    }
}
