package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class NotAccountOwnerException extends ProjectException {
    public NotAccountOwnerException() {
        super("登入者帳號不符合");
    }

    @Override
    public String getErrorCode() {
        return "User - NotOwner";
    }
}
