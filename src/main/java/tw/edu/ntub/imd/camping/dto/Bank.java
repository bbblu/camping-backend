package tw.edu.ntub.imd.camping.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.edu.ntub.imd.camping.enumerate.BankType;

@Schema(name = "金融機構", description = "金融機構")
@Data
public class Bank {
    @Schema(description = "金融機構代號", example = "001")
    private String id;
    @Schema(description = "金融機構類型(0: 銀行/ 1: 信合社/ 2: 農會/ 3: 漁會/ 4: 郵局)", example = "0")
    private BankType type;
    @Schema(description = "金融機構名稱", example = "中央信託")
    private String name;

    @Hidden
    public String getTypeName() {
        return type.name;
    }
}
