package tw.edu.ntub.imd.camping.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.edu.ntub.imd.camping.enumerate.BankType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Schema(name = "金融機構帳號", description = "金融機構帳號")
@Data
public class BankAccount {
    @Schema(description = "帳號", example = "1234567891234567")
    @NotBlank(message = "帳號 - 未填寫")
    @Size(max = 16, message = "帳號 - 輸入字數大於{max}個字")
    private String account;
    @Schema(description = "金融機構代號", example = "700")
    @Size(max = 3, message = "金融機構代號 - 輸入字數大於{max}個字")
    private String bankId;
    @Schema(type = "int", description = "金融機構類型", minimum = "0", maximum = "4", example = "4")
    private BankType bankType;
    @Schema(description = "金融機構名稱", example = "中華郵政")
    @Size(max = 40, message = "金融機構名稱 - 輸入字數大於{max}個字")
    private String bankName;
    @Schema(description = "餘額", minimum = "0", example = "5000")
    @PositiveOrZero(message = "餘額 - 應大於等於0")
    private Integer money;
}
