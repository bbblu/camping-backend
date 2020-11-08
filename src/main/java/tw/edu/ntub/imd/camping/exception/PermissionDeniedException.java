package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class PermissionDeniedException extends ProjectException {
    public PermissionDeniedException() {
        super("您沒有權限進行此操作");
    }

    @Override
    public String getErrorCode() {
        return "Permission - Denied";
    }
}
