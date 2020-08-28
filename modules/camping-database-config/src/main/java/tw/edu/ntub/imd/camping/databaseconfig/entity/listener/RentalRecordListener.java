package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class RentalRecordListener {

    @PrePersist
    public void preSave(RentalRecord rentalRecord) {
        if (rentalRecord.isEnable() == null) {
            rentalRecord.setEnable(true);
        }
        if (rentalRecord.getStatus() == null) {
            rentalRecord.setStatus(RentalRecordStatus.NOT_PICK_UP);
        }
        if (rentalRecord.getRenterAccount() == null) {
            rentalRecord.setRenterAccount(SecurityUtils.getLoginUserAccount());
        }
        if (rentalRecord.getRentalDate() == null) {
            rentalRecord.setRentalDate(LocalDateTime.now());
        }
        if (rentalRecord.getLastModifyAccount() == null) {
            rentalRecord.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        }
        if (rentalRecord.getLastModifyDate() == null) {
            rentalRecord.setLastModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(RentalRecord rentalRecord) {
        rentalRecord.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        rentalRecord.setLastModifyDate(LocalDateTime.now());
        switch (rentalRecord.getStatus()) {
            case CANCEL:
                rentalRecord.setCancelDate(LocalDateTime.now());
                break;
            case NOT_PICK_UP:
                break;
            case NOT_RETURN:
                rentalRecord.setPickDate(LocalDateTime.now());
                break;
            case RETRIEVE:
                rentalRecord.setReturnDate(LocalDateTime.now());
                break;
            case CHECKED:
                rentalRecord.setCheckDate(LocalDateTime.now());
                break;
        }
    }
}
