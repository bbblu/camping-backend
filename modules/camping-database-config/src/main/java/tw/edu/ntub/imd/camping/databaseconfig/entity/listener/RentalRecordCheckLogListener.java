package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordCheckLog;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class RentalRecordCheckLogListener {
    @PrePersist
    public void preSave(RentalRecordCheckLog log) {
        if (log.getCheckerAccount() == null) {
            log.setCheckerAccount(SecurityUtils.getLoginUserAccount());
        }
        if (log.getCheckDate() == null) {
            log.setCheckDate(LocalDateTime.now());
        }
    }
}
