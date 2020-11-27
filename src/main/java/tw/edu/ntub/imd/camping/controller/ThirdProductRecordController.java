package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.PoiWorkbook;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.Workbook;
import tw.edu.ntub.imd.camping.service.ThirdPartyProductRecordService;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;

import java.io.IOException;

@AllArgsConstructor
@Tag(name = "ThirdPartyProduct")
@RestController
@RequestMapping(path = "/third-party-product")
public class ThirdProductRecordController {
    private final ThirdPartyProductRecordService thirdPartyProductRecordService;

    @PostMapping(path = "/import")
    public ResponseEntity<String> importRecord(@RequestParam(name = "file") MultipartFile file) throws IOException {
        Workbook workbook = new PoiWorkbook(file.getInputStream());
        thirdPartyProductRecordService.importRecord(workbook);
        return ResponseEntityBuilder.buildSuccessMessage("匯入成功");
    }
}
