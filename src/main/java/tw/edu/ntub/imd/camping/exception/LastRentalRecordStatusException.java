package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class LastRentalRecordStatusException extends ProjectException {
    public LastRentalRecordStatusException() {
        super("此紀錄狀態已為已完成");
    }

    @Override
    public String getErrorCode() {
        return "RentalRecord - Status";
    }
}
