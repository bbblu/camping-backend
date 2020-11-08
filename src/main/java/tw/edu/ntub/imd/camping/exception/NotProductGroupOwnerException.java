package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class NotProductGroupOwnerException extends ProjectException {
    public NotProductGroupOwnerException(int id, String account) {
        super("此帳號不為該商品群組的擁有者：" + id + ", " + account);
    }

    @Override
    public String getErrorCode() {
        return "ProductGroup - Mismatch";
    }
}
