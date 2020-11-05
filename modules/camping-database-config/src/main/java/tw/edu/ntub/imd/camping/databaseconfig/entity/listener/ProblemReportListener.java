package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProblemReport;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportStatus;
import tw.edu.ntub.imd.camping.databaseconfig.exception.EmptyProblemReportHandleResult;
import tw.edu.ntub.imd.camping.databaseconfig.exception.NoProblemReportHandlerException;

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
        if (problemReport.getLastModifyAccount() == null) {
            problemReport.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        }
        if (problemReport.getLastModifyDate() == null) {
            problemReport.setLastModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(ProblemReport problemReport) {
        switch (problemReport.getStatus()) {
            case UNPROCESSED:
                break;
            case PROCESSED:
                if (problemReport.getHandler() == null) {
                    throw new NoProblemReportHandlerException();
                }
                problemReport.setHandleDate(LocalDateTime.now());
                break;
            case COMPLETE:
                if (problemReport.getHandleResult() == null) {
                    throw new EmptyProblemReportHandleResult();
                }
                problemReport.setCompleteDate(LocalDateTime.now());
                break;
        }
        problemReport.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        problemReport.setLastModifyDate(LocalDateTime.now());
    }
}
