package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class UnknownProductGroupPriceRangeException extends ProjectException {
    public UnknownProductGroupPriceRangeException(int ordinal) {
        super("不支援的價格範圍：" + ordinal);
    }

    @Override
    public String getErrorCode() {
        return "ProductGroupPriceRange - Unknown";
    }
}
