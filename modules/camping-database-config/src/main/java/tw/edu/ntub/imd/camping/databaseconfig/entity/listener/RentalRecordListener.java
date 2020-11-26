package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

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
            rentalRecord.setStatus(RentalRecordStatus.NOT_AGREE);
        }
    }

    @PreUpdate
    public void preUpdate(RentalRecord rentalRecord) {
        switch (rentalRecord.getStatus()) {
            case ALREADY_CANCEL:
                rentalRecord.setCancelDate(LocalDateTime.now());
            case NOT_PAY:
                rentalRecord.setAgreeDate(LocalDateTime.now());
            case NOT_PLACE:
                rentalRecord.setPaymentDate(LocalDateTime.now());
            case NOT_PICK_UP:
                rentalRecord.setPlacedDate(LocalDateTime.now());
                break;
            case NOT_RETURN:
                rentalRecord.setPickDate(LocalDateTime.now());
                break;
            case NOT_RETRIEVE:
                rentalRecord.setReturnDate(LocalDateTime.now());
                break;
            case NOT_COMMENT:
                rentalRecord.setBackDate(LocalDateTime.now());
                break;
        }
    }
}
