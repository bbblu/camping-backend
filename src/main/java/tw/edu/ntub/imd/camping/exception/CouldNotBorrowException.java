package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class CouldNotBorrowException extends ProjectException {
    public CouldNotBorrowException() {
        super("租借失敗，無此商品或該商品已出借");
    }

    @Override
    public String getErrorCode() {
        return "Rental - ProductGroupNotEnableOrBorrowed";
    }
}
