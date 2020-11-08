package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordStatusChangeLog;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class RentalRecordStatusChangeLogListener {
    @PrePersist
    public void preSave(RentalRecordStatusChangeLog log) {
        if (log.getChangerAccount() == null) {
            log.setChangerAccount(SecurityUtils.getLoginUserAccount());
        }
        if (log.getChangeDate() == null) {
            log.setChangeDate(LocalDateTime.now());
        }
    }
}
