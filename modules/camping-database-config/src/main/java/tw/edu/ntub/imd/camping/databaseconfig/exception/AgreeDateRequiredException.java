package tw.edu.ntub.imd.camping.databaseconfig.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class AgreeDateRequiredException extends ProjectException {
    public AgreeDateRequiredException() {
        super("未填寫出借方同意時間或買方同意時間");
    }

    @Override
    public String getErrorCode() {
        return "CancelRentalRecord - RequireAgreeDate";
    }
}
