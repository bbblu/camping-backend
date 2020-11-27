package tw.edu.ntub.imd.camping.service;

import org.springframework.lang.Nullable;
import tw.edu.ntub.imd.camping.bean.ThirdPartyProductRecordIndexBean;
import tw.edu.ntub.imd.camping.bean.ThirdPartyProductRecordIndexFilterBean;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.Workbook;

import java.util.List;

public interface ThirdPartyProductRecordService {
    default List<ThirdPartyProductRecordIndexBean> searchAllIndexRecord() {
        return searchIndexRecord(null);
    }

    List<ThirdPartyProductRecordIndexBean> searchIndexRecord(
            @Nullable ThirdPartyProductRecordIndexFilterBean filterBean
    );

    void importRecord(Workbook recordWorkbook);
}
