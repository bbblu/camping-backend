package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ProblemReportBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProblemReport;
import tw.edu.ntub.imd.camping.service.transformer.ProblemReportTransformer;

import javax.annotation.Nonnull;

@Component
public class ProblemReportTransformerImpl implements ProblemReportTransformer {
    @Nonnull
    @Override
    public ProblemReport transferToEntity(@Nonnull ProblemReportBean problemReportBean) {
        return JavaBeanUtils.copy(problemReportBean, new ProblemReport());
    }

    @Nonnull
    @Override
    public ProblemReportBean transferToBean(@Nonnull ProblemReport problemReport) {
        return JavaBeanUtils.copy(problemReport, new ProblemReportBean());
    }
}
