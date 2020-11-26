package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.UserCompensateRecord;

import javax.persistence.PrePersist;

public class UserCompensateRecordListener {
    @PrePersist
    public void preSave(UserCompensateRecord userCompensateRecord) {
        if (userCompensateRecord.isCompensated() == null) {
            userCompensateRecord.setCompensated(false);
        }
    }
}
