package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

public class ChangeRentalRecordStatusFailException extends ProjectException {
    public ChangeRentalRecordStatusFailException(RentalRecordStatus fromStatus, RentalRecordStatus toStatus) {
        super("狀態變更失敗，無法從" + fromStatus + "變更至" + toStatus);
    }

    @Override
    public String getErrorCode() {
        return "RentalRecord - ChangeStatusFail";
    }
}
