package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class InvalidOldPasswordException extends ProjectException {
    public InvalidOldPasswordException() {
        super("舊密碼不正確");
    }

    @Override
    public String getErrorCode() {
        return "User - old password not valid";
    }
}
