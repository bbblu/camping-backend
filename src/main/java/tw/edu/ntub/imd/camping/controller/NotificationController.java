package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.camping.bean.NotificationBean;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.service.NotificationService;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.object.SingleValueObjectData;

@AllArgsConstructor
@Tag(name = "Notification", description = "租借紀錄通知")
@RestController
@RequestMapping(path = "/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(
            tags = "Notification",
            method = "GET",
            summary = "查詢通知未讀數",
            description = "查詢登入者的未讀數",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationNotReadCountSchema.class)
                    )
            )
    )
    @GetMapping(path = "/not-read-count")
    public ResponseEntity<String> getNotReadCount() {
        return ResponseEntityBuilder.success("查詢成功")
                .data(SingleValueObjectData.create(
                        "count",
                        notificationService.getNotReadCount(SecurityUtils.getLoginUserAccount())
                ))
                .build();
    }

    @Operation(
            tags = "Notification",
            method = "GET",
            summary = "查詢通知列表",
            description = "查詢登入者的所有通知並同時更新閱讀時間",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationBean.class)
                    )
            )
    )
    @GetMapping(path = "")
    public ResponseEntity<String> searchAll() {
        return ResponseEntityBuilder.success("查詢成功")
                .data(notificationService.searchByUserAccount(SecurityUtils.getLoginUserAccount()), (data, notificationBean) -> {
                    data.add("id", notificationBean.getId());
                    data.add("rentalRecordId", notificationBean.getRentalRecordId());
                    data.add("type", notificationBean.getType().ordinal());
                    data.add("userAccount", notificationBean.getUserAccount());
                    data.add("content", notificationBean.getContent());
                    data.add("sendDate", notificationBean.getSendDate());
                    data.add("readDate", notificationBean.getReadDate());
                })
                .build();
    }

    @Schema(name = "通知未讀數", description = "通知未讀數")
    @Data
    private static class NotificationNotReadCountSchema {
        @Schema(description = "未讀數", minimum = "0", example = "16")
        private Integer count;
    }
}
