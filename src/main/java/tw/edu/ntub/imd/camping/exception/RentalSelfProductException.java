package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class RentalSelfProductException extends ProjectException {
    public RentalSelfProductException() {
        super("無法租借自己上架的商品");
    }

    @Override
    public String getErrorCode() {
        return "Rental - SelfProduct";
    }
}
