package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ProblemReportBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProblemReport;
import tw.edu.ntub.imd.camping.service.transformer.ProblemReportTransformer;

@Component
public class ProblemReportTransformerImpl implements ProblemReportTransformer {
    @NonNull
    @Override
    public ProblemReport transferToEntity(@NonNull ProblemReportBean problemReportBean) {
        return JavaBeanUtils.copy(problemReportBean, new ProblemReport());
    }

    @NonNull
    @Override
    public ProblemReportBean transferToBean(@NonNull ProblemReport problemReport) {
        return JavaBeanUtils.copy(problemReport, new ProblemReportBean());
    }
}
