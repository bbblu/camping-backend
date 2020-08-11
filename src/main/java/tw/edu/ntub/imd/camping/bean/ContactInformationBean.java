package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Schema(name = "聯絡方式", description = "聯絡方式")
@Data
@EqualsAndHashCode
public class ContactInformationBean {
    @Hidden
    private Integer id;
    @Hidden
    private String userAccount;
    @Schema(description = "聯絡方式內容", example = "LineId：12345 Telegram：6789")
    @NotBlank(message = "聯絡方式 - 未填寫")
    @Size(max = 150, message = "聯絡方式 - 輸入字數大於{max}個字")
    private String content;
    @Hidden
    private LocalDateTime createDate;
}
