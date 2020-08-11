package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ContactInformation;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class ContactInformationListener {

    @PrePersist
    private void beforeSave(ContactInformation contactInformation) {
        if (contactInformation.isEnable() == null) {
            contactInformation.setEnable(true);
        }
        if (contactInformation.getUserAccount() == null) {
            contactInformation.setUserAccount(SecurityUtils.getLoginUserAccount());
        }
        if (contactInformation.getCreateDate() == null) {
            contactInformation.setCreateDate(LocalDateTime.now());
        }
        if (contactInformation.getLastModifyDate() == null) {
            contactInformation.setLastModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    private void beforeUpdate(ContactInformation contactInformation) {
        contactInformation.setLastModifyDate(LocalDateTime.now());
    }
}
