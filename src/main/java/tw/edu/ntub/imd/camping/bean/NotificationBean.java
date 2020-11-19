package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.NotificationType;

import java.time.LocalDateTime;

@Schema(name = "通知", description = "通知")
@Data
public class NotificationBean {
    @Schema(description = "編號", example = "1")
    private Integer id;
    @Schema(description = "租借紀錄編號", example = "1")
    private Integer rentalRecordId;
    @Schema(description = "通知類型", type = "integer", example = "1")
    private NotificationType type;
    @Schema(description = "通知對象帳號", example = "admin")
    private String userAccount;
    @Schema(description = "通知內容", example = "您的商品有人提出租借，請至租借紀錄查看")
    private String content;
    @Schema(description = "通知時間", example = "2020/11/19 16:15:29")
    private LocalDateTime sendDate;
    @Schema(description = "已讀時間", example = "2020/11/19 16:15:29")
    private LocalDateTime readDate;
}
