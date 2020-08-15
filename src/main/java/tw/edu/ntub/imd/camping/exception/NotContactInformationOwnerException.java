package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class NotContactInformationOwnerException extends ProjectException {
    public NotContactInformationOwnerException(int id, String account) {
        super("此帳號不為該聯絡方式的擁有者：" + id + ", " + account);
    }

    @Override
    public String getErrorCode() {
        return "ContactInformation - Mismatch";
    }
}
