package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.LogRecord;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class LogRecordListener {

    @PrePersist
    private void preSave(LogRecord logRecord) {
        if (logRecord.getExecuteDate() == null) {
            logRecord.setExecuteDate(LocalDateTime.now());
        }
    }
}
