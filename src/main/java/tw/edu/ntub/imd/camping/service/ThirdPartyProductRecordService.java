package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.dto.file.excel.workbook.Workbook;

public interface ThirdPartyProductRecordService {
    void importRecord(Workbook recordWorkbook);
}
