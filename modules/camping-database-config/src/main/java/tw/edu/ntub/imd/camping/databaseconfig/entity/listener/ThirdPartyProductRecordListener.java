package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ThirdPartyProductRecord;

import javax.persistence.PrePersist;

public class ThirdPartyProductRecordListener {

    @PrePersist
    public void preSave(ThirdPartyProductRecord thirdPartyProductRecord) {
        if (thirdPartyProductRecord.isEnable() == null) {
            thirdPartyProductRecord.setEnable(true);
        }
    }
}
