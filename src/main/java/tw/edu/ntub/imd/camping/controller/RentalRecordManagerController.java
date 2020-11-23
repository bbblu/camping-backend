package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.edu.ntub.imd.camping.bean.RentalRecordIndexFilterBean;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.service.RentalRecordService;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/rental/manager")
@PreAuthorize("hasAnyAuthority('Administrator', 'Manager')")
public class RentalRecordManagerController {
    private final RentalRecordService rentalRecordService;

    @GetMapping(path = "")
    public ModelAndView indexPage() {
        ModelAndView modelAndView = new ModelAndView("/rental_record/index");
        modelAndView.addObject("statusList", RentalRecordStatus.values());
        modelAndView.addObject("rentalRecordList", rentalRecordService.searchIndexBean());
        return modelAndView;
    }

    @GetMapping(path = "/filter")
    public ResponseEntity<String> searchIndexBean(RentalRecordIndexFilterBean filter) {
        return ResponseEntityBuilder.success("查詢成功")
                .data(rentalRecordService.searchIndexBean(filter), (data, indexBean) -> {
                    data.add("id", indexBean.getId());
                    data.add("idString", indexBean.getIdString());
                    data.add("status", indexBean.getStatus().toString());
                    data.add("rentalDate", indexBean.getRentalDate());
                    data.add("description", indexBean.getLastChangeStatusDescription());
                })
                .build();
    }

    @Schema(name = "檢查紀錄", description = "新增檢查紀錄時的傳遞資料")
    @Data
    private static class CreateCheckLogSchema {
        @Schema(description = "檢查紀錄", example = "缺少椅子、帳篷")
        private String description;
    }
}
