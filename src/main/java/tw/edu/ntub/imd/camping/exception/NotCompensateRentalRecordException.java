package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class NotCompensateRentalRecordException extends ProjectException {
    public NotCompensateRentalRecordException() {
        super("您尚未賠償上一次的租借");
    }

    @Override
    public String getErrorCode() {
        return "RentalRecord - NotCompensate";
    }
}
