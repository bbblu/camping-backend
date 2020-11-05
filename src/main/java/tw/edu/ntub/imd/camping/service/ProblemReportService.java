package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.ProblemReportBean;

public interface ProblemReportService extends BaseService<ProblemReportBean, Integer> {
    void updateHandler(int id);

    void updateHandleResult(int id, String handleResult);
}
