package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class CreditCardTransactionException extends ProjectException {

    public CreditCardTransactionException(String message) {
        super(message);
    }

    public CreditCardTransactionException(Throwable cause) {
        super("信用卡交易失敗", cause);
    }

    @Override
    public String getErrorCode() {
        return "CreditCardTransaction - Fail";
    }
}
