package tw.edu.ntub.imd.camping.exception.form;

public class InvalidFormNumberFormatException extends InvalidRequestFormatException {

    public InvalidFormNumberFormatException(NumberFormatException e) {
        super(e.getMessage().substring(18) + "不能轉換成數字");
    }
}
