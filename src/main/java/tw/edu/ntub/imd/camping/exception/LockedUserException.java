package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class LockedUserException extends ProjectException {
    public LockedUserException() {
        super("您已被鎖定，請付清賠償金額後才可進行此操作。");
    }

    @Override
    public String getErrorCode() {
        return "User - Locked";
    }
}
