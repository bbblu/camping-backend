package tw.edu.ntub.imd.camping.databaseconfig.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class EmptyCheckResultException extends ProjectException {
    public EmptyCheckResultException() {
        super("檢查結果 - 未填寫");
    }

    @Override
    public String getErrorCode() {
        return "RentalRecord - EmptyCheckResult";
    }
}
