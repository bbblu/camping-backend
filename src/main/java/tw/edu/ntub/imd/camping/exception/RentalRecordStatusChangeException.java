package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

public class RentalRecordStatusChangeException extends ProjectException {
    public RentalRecordStatusChangeException(RentalRecordStatus oldStatus, RentalRecordStatus newStatus) {
        super("無法從" + oldStatus.name() + "轉換至" + newStatus.name());
    }

    @Override
    public String getErrorCode() {
        return "Rental - Status";
    }
}
