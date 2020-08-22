package tw.edu.ntub.imd.camping.dto;

import lombok.Data;
import tw.edu.ntub.imd.camping.enumerate.BankType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
public class BankAccount {
    @NotBlank(message = "帳號 - 未填寫")
    @Size(max = 16, message = "帳號 - 輸入字數大於{max}個字")
    private String account;
    @NotBlank(message = "銀行代號 - 未填寫")
    @Size(max = 3, message = "銀行代號 - 輸入字數大於{max}個字")
    private String bankId;
    @NotNull(message = "金融機構類型 - 未填寫")
    private BankType bankType;
    @NotBlank(message = "金融機構名稱 - 未填寫")
    @Size(max = 40, message = "金融機構名稱 - 輸入字數大於{max}個字")
    private String bankName;
    @PositiveOrZero(message = "餘額 - 應大於等於0")
    private Integer money;
}
