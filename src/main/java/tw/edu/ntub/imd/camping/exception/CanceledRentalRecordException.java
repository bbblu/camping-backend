package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class CanceledRentalRecordException extends ProjectException {
    public CanceledRentalRecordException(int id) {
        super("此租借紀錄已被取消：" + id);
    }

    @Override
    public String getErrorCode() {
        return "RentalRecord - Canceled";
    }
}
