package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class NotProductOwner extends ProjectException {
    public NotProductOwner(String account) {
        super("此帳號不為部份商品的擁有者：" + account);
    }

    @Override
    public String getErrorCode() {
        return "Product - Mismatch";
    }
}
