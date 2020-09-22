package tw.edu.ntub.imd.camping.databaseconfig.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class EmptyCancelDetailException extends ProjectException {
    public EmptyCancelDetailException() {
        super("取消原因 - 未填寫");
    }

    @Override
    public String getErrorCode() {
        return "RentalRecord - EmptyCancelDetail";
    }
}
