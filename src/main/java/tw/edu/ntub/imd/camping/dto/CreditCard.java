package tw.edu.ntub.imd.camping.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.YearMonth;

@Schema(name = "信用卡資料", description = "信用卡資料")
@Data
@EqualsAndHashCode
public class CreditCard {
    @Schema(description = "信用卡卡號", example = "1234567891234567")
    @NotBlank(message = "信用卡卡號 - 未填寫")
    @Pattern(regexp = "^[0-9]{16}$", message = "信用卡卡號 - 應為16位數字")
//    @CreditCardNumber(message = "信用卡卡號 - 格式不符")
    private String cardId;

    @Schema(description = "安全碼", example = "1234")
    @NotBlank(message = "安全碼 - 未填寫")
    @Pattern(regexp = "^[0-9]{3,4}$", message = "信用卡卡號 - 應為3到4位數字")
    private String safeCode;

    @Schema(type = "string", description = "過期時間(MM/yy，例如：2020年9月過期，則為09/20)", example = "09/20")
    @NotNull(message = "過期時間 - 未填寫")
    private YearMonth expireDate;

    @Schema(description = "帳單地址", example = "台北市中正區濟南路321號")
    @NotBlank(message = "帳單地址 - 未填寫")
    @Size(max = 300, message = "帳單地址 - 輸入字數大於{max}個字")
    private String billAddress;

    @Schema(description = "帳單姓氏", example = "王")
    @NotBlank(message = "帳單姓氏 - 未填寫")
    @Size(max = 50, message = "帳單姓氏 - 輸入字數大於{max}個字")
    private String billLastName;

    @Schema(description = "帳單名字", example = "小明")
    @NotBlank(message = "帳單名字 - 未填寫")
    @Size(max = 50, message = "帳單名字 - 輸入字數大於{max}個字")
    private String billFirstName;

    @Schema(description = "帳單城市", example = "台北")
    @NotBlank(message = "帳單城市 - 未填寫")
    @Size(max = 50, message = "帳單城市 - 輸入字數大於{max}個字")
    private String billCity;

    @Schema(description = "帳單郵遞區號", example = "21314")
    @NotBlank(message = "帳單郵遞區號 - 未填寫")
    @Pattern(regexp = "^[0-9]{3,6}$", message = "帳單郵遞區號 - 應為3到6位數字")
    private String billZipCode;

    @Schema(description = "帳單國家", example = "臺灣")
    @NotBlank(message = "帳單國家 - 未填寫")
    @Size(max = 50, message = "帳單國家/地區 - 輸入字數大於{max}個字")
    private String billCountry;

    @Schema(description = "帳單電話號碼", example = "0901234567")
    @NotBlank(message = "帳單電話號碼 - 未填寫")
    @Pattern(regexp = "^09[0-9]{8}$", message = "帳單電話號碼 - 格式不符合電話號碼格式：09XXXXXXXX")
    private String billCellPhone;
}
