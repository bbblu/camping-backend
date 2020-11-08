package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordCancel;
import tw.edu.ntub.imd.camping.databaseconfig.exception.AgreeDateRequiredException;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class RentalRecordCancelListener {

    @PrePersist
    public void preSave(RentalRecordCancel rentalRecordCancel) {
        if (rentalRecordCancel.getProductOwnerAgreeDate() == null && rentalRecordCancel.getRenterAgreeDate() == null) {
            throw new AgreeDateRequiredException();
        }
        if (rentalRecordCancel.getCreateAccount() == null) {
            rentalRecordCancel.setCreateAccount(SecurityUtils.getLoginUserAccount());
        }
        if (rentalRecordCancel.getLastModifyAccount() == null) {
            rentalRecordCancel.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        }
        if (rentalRecordCancel.getLastModifyDate() == null) {
            rentalRecordCancel.setLastModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(RentalRecordCancel rentalRecordCancel) {
        rentalRecordCancel.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        rentalRecordCancel.setLastModifyDate(LocalDateTime.now());
    }
}
