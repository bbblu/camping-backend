package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.UserBadRecord;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class UserBadRecordListener {
    @PrePersist
    public void preSave(UserBadRecord userBadRecord) {
        if (userBadRecord.getRecordDate() == null) {
            userBadRecord.setRecordDate(LocalDateTime.now());
        }
    }
}
