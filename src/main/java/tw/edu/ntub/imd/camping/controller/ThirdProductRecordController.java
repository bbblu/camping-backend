package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;

import java.io.IOException;

@AllArgsConstructor
@Tag(name = "ThirdPartyProduct")
@Controller
@RequestMapping(path = "/third-party-product")
public class ThirdProductRecordController {
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
                .data(thirdPartyProductRecordService.searchIndexRecord(filter), (data, index) -> {
                    data.add("id", index.getId());
                    data.add("brandName", index.getBrandName());
                    data.add("typeName", index.getTypeName());
                    data.add("subTypeName", index.getSubTypeName());
                    data.add("price", index.getPrice());
                })
                .build();
    }

    @PostMapping(path = "/import")
    public ResponseEntity<String> importRecord(@RequestParam(name = "file") MultipartFile file) throws IOException {
        Workbook workbook = new PoiWorkbook(file.getInputStream());
        thirdPartyProductRecordService.importRecord(workbook);
        return ResponseEntityBuilder.buildSuccessMessage("匯入成功");
    }
}
