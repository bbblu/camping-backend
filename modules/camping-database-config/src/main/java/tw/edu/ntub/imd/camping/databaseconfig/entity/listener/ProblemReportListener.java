package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProblemReport;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportStatus;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class ProblemReportListener {

    @PrePersist
    public void preSave(ProblemReport problemReport) {
        if (problemReport.getStatus() == null) {
            problemReport.setStatus(ProblemReportStatus.UNPROCESSED);
        }
        if (problemReport.getReportDate() == null) {
            problemReport.setReportDate(LocalDateTime.now());
        }
        if (problemReport.getModifyDate() == null) {
            problemReport.setModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(ProblemReport problemReport) {
        problemReport.setModifyId(SecurityUtils.getLoginUserAccount());
        problemReport.setModifyDate(LocalDateTime.now());
    }
}
