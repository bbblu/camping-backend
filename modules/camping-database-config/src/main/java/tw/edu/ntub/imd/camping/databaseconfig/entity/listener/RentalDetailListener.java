package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalDetail;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalDetailStatus;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class RentalDetailListener {

    @PrePersist
    private void preSave(RentalDetail rentalDetail) {
        if (rentalDetail.getStatus() == null) {
            rentalDetail.setStatus(RentalDetailStatus.NOT_RETURN);
        }
        if (rentalDetail.getLastModifyDate() == null) {
            rentalDetail.setLastModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    private void preUpdate(RentalDetail rentalDetail) {
        rentalDetail.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        rentalDetail.setLastModifyDate(LocalDateTime.now());
    }
}
