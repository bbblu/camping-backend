package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalDetail;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalDetailStatus;

import javax.persistence.PrePersist;

public class RentalDetailListener {

    @PrePersist
    private void preSave(RentalDetail rentalDetail) {
        if (rentalDetail.getStatus() == null) {
            rentalDetail.setStatus(RentalDetailStatus.NOT_RETURN);
        }
    }
}
