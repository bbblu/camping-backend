package tw.edu.ntub.imd.camping.exception;

import lombok.Getter;
import tw.edu.ntub.birc.common.exception.ProjectException;

public class CreditCardTransactionException extends ProjectException {
    @Getter
    private String responseErrorCode;

    public CreditCardTransactionException(String message) {
        super(message);
    }

    public CreditCardTransactionException(String message, String responseErrorCode) {
        super(message);
        this.responseErrorCode = responseErrorCode;
    }

    public CreditCardTransactionException(Throwable cause) {
        super("信用卡交易失敗", cause);
    }

    @Override
    public String getErrorCode() {
        return "CreditCardTransaction - Fail";
    }
}
