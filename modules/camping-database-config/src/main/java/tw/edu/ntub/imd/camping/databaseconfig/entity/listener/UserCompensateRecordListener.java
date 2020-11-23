package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.UserCompensateRecord;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class UserCompensateRecordListener {
    @PrePersist
    public void preSave(UserCompensateRecord userCompensateRecord) {
        if (userCompensateRecord.isCompensated() == null) {
            userCompensateRecord.setCompensated(false);
        }
        if (userCompensateRecord.getCreateDate() == null) {
            userCompensateRecord.setCreateDate(LocalDateTime.now());
        }
    }
}
