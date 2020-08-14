package tw.edu.ntub.imd.camping.enumerate;

import tw.edu.ntub.birc.common.util.MathUtils;

public enum ProductGroupPriceRange {
    ZERO_TO_TWO_THOUSANDS(0, 2000),
    TWO_THOUSANDS_ONE_TO_FOUR_THOUSANDS(2001, 4000);

    public final int start;
    public final int end;

    ProductGroupPriceRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean isInRange(int price) {
        return MathUtils.isInRange(price, start, end);
    }
}
