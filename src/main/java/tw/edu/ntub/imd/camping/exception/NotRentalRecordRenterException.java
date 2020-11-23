package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class NotRentalRecordRenterException extends ProjectException {
    public NotRentalRecordRenterException(int id, String account) {
        super("此帳號不為此紀錄(" + id + ")的租借人：" + account);
    }

    @Override
    public String getErrorCode() {
        return "RentalRecord - Owner";
    }
}
