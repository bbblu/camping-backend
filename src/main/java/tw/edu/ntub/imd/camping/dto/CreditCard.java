package tw.edu.ntub.imd.camping.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class CreditCard {
    @NotBlank(message = "信用卡卡號 - 未填寫")
    @Size(min = 16, max = 16, message = "信用卡卡號 - 應為16個字")
//    @CreditCardNumber(message = "信用卡卡號 - 格式不符")
    private String cardId;

    @NotBlank(message = "安全碼 - 未填寫")
    @Size(min = 3, max = 4, message = "安全碼 - 應為{min}到{max}個字")
    private String safeCode;

    @NotNull(message = "過期時間 - 未填寫")
    private LocalDate expireDate;

    @NotNull(message = "交易金額 - 未填寫")
    @PositiveOrZero(message = "交易金額 - 應為大於等於0的數字")
    private Integer money;

    @NotBlank(message = "帳單地址 - 未填寫")
    @Size(max = 300, message = "帳單地址 - 輸入字數大於{max}個字")
    private String billAddress;

    @NotBlank(message = "帳單姓氏 - 未填寫")
    @Size(max = 50, message = "帳單姓氏 - 輸入字數大於{max}個字")
    private String billLastName;

    @NotBlank(message = "帳單名字 - 未填寫")
    @Size(max = 50, message = "帳單名字 - 輸入字數大於{max}個字")
    private String billFirstName;

    @NotBlank(message = "帳單城市 - 未填寫")
    @Size(max = 50, message = "帳單城市 - 輸入字數大於{max}個字")
    private String billCity;

    @NotBlank(message = "帳單郵遞區號 - 未填寫")
    @Size(max = 6, message = "帳單郵遞區號 - 輸入字數大於{max}個字")
    private String billZipCode;

    @NotBlank(message = "帳單國家 - 未填寫")
    @Size(max = 50, message = "帳單國家/地區 - 輸入字數大於{max}個字")
    private String billCountry;

    @NotBlank(message = "帳單電話號碼 - 未填寫")
    @Size(max = 10, message = "帳單電話號碼 - 輸入字數大於{max}個字")
    private String billCellPhone;
}
