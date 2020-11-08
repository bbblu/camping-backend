package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

public class RequestCancelRentalRecordFailException extends ProjectException {
    public RequestCancelRentalRecordFailException(RentalRecordStatus currentStatus) {
        super("請求取消失敗，目前狀態不允許請求：" + currentStatus);
    }

    @Override
    public String getErrorCode() {
        return "RentalRecord - RequestCancelFail";
    }
}
