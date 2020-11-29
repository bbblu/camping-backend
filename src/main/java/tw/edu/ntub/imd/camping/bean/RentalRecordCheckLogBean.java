package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "商品狀況")
public class RentalRecordCheckLogBean {
    @Hidden
    private Integer id;

    @Hidden
    private Integer recordId;

    @Hidden
    private RentalRecordStatus recordStatus;

    @Schema(description = "商品狀況描述", example = "與上架資訊一致")
    @NotBlank(message = "未填寫商品狀況")
    private String content;

    @Hidden
    private List<String> imageUrlList;

    @Schema(description = "商品狀況圖陣列")
    private MultipartFile[] images;

    public RentalRecordCheckLogBean(String content) {
        this.content = content;
    }
}
