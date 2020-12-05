package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import tw.edu.ntub.imd.camping.bean.ThirdPartyProductRecordIndexFilterBean;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.PoiWorkbook;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.Workbook;
import tw.edu.ntub.imd.camping.service.ProductGroupService;
import tw.edu.ntub.imd.camping.service.ThirdPartyProductRecordService;
import tw.edu.ntub.imd.camping.util.http.ExcelResponseUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;

@RequiredArgsConstructor
@Tag(name = "ThirdPartyProduct")
@Controller
@RequestMapping(path = "/third-party-product")
public class ThirdPartyProductRecordController {
    private static final DecimalFormat PRICE_FORMATTER = new DecimalFormat("#,###");
    private final ProductGroupService productGroupService;
    private final ThirdPartyProductRecordService thirdPartyProductRecordService;

    @GetMapping(path = "")
    public ModelAndView indexPage() {
        ModelAndView modelAndView = new ModelAndView("/third_party_record/index");
        modelAndView.addObject("brandList", productGroupService.searchAllBrand());
        return modelAndView;
    }

    @GetMapping(path = "/record")
    public ResponseEntity<String> searchRecord(
            ThirdPartyProductRecordIndexFilterBean filter
    ) {
        return ResponseEntityBuilder.success("查詢成功")
                .data(thirdPartyProductRecordService.searchIndexRecord(filter), (data, index, indexBean) -> {
                    data.add("id", index + 1);
                    data.add("brandName", indexBean.getBrandName());
                    data.add("typeName", indexBean.getTypeName());
                    data.add("subTypeName", indexBean.getSubTypeName());
                    data.add("price", PRICE_FORMATTER.format(indexBean.getPrice()));
                })
                .build();
    }

    @GetMapping(path = "/template")
    public void downloadTemplate(HttpServletResponse response) {
        Workbook workbook = thirdPartyProductRecordService.getTemplateExcel();
        ExcelResponseUtils.response(response, workbook);
    }

    @PostMapping(path = "/import")
    public ResponseEntity<String> importRecord(
            @RequestParam MultipartFile file,
            @RequestParam boolean isReplaceOldRecord
    ) throws IOException {
        Workbook workbook = new PoiWorkbook(file.getInputStream());
        thirdPartyProductRecordService.importRecord(workbook, isReplaceOldRecord);
        return ResponseEntityBuilder.buildSuccessMessage("匯入成功");
    }

    @GetMapping(path = "/record/export")
    public void downloadRecord(HttpServletResponse response) {
        Workbook workbook = thirdPartyProductRecordService.getRecordExcel();
        ExcelResponseUtils.response(response, workbook);
    }
}
