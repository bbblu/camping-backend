package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.camping.bean.ProblemReportBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProblemReportDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProblemReport;
import tw.edu.ntub.imd.camping.service.ProblemReportService;
import tw.edu.ntub.imd.camping.service.transformer.ProblemReportTransformer;

@Service
public class ProblemReportServiceImpl extends BaseServiceImpl<ProblemReportBean, ProblemReport, Integer> implements ProblemReportService {
    private final ProblemReportDAO problemReportDAO;
    private final ProblemReportTransformer transformer;

    @Autowired
    public ProblemReportServiceImpl(ProblemReportDAO problemReportDAO, ProblemReportTransformer transformer) {
        super(problemReportDAO, transformer);
        this.problemReportDAO = problemReportDAO;
        this.transformer = transformer;
    }

    @Override
    public ProblemReportBean save(ProblemReportBean problemReportBean) {
        ProblemReport problemReport = transformer.transferToEntity(problemReportBean);
        ProblemReport saveResult = problemReportDAO.saveAndFlush(problemReport);
        return transformer.transferToBean(saveResult);
    }
}
