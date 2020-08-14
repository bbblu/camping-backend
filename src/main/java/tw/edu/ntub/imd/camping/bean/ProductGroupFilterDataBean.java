package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.birc.common.util.ArrayUtils;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.enumerate.ProductGroupPriceRange;

import java.time.LocalDate;
import java.util.Arrays;

@Hidden
@Data
@EqualsAndHashCode
public class ProductGroupFilterDataBean {
    private LocalDate borrowStartDate;
    private LocalDate borrowEndDate;
    private String cityAreaName;
    private int[] typeArray;
    private ProductGroupPriceRange priceRange;

    public boolean isBorrowStartDateNullOrBefore(LocalDate borrowStartDate) {
        return this.borrowStartDate == null || this.borrowStartDate.isBefore(borrowStartDate) || this.borrowStartDate.isEqual(borrowStartDate);
    }

    public boolean isBorrowEndDateNullOrAfter(LocalDate borrowEndDate) {
        return this.borrowEndDate == null || this.borrowEndDate.isAfter(borrowEndDate) || this.borrowEndDate.isEqual(borrowEndDate);
    }

    public boolean isCityAreaNameNullOrEquals(String cityAreaName) {
        return this.cityAreaName == null || StringUtils.isEquals(this.cityAreaName, cityAreaName);
    }

    public boolean isTypeArrayNullOrAllMatchContains(String type) {
        if (this.typeArray == null) {
            return true;
        } else {
            return Arrays.stream(type.split(","))
                    .mapToInt(Integer::parseInt)
                    .filter(id -> ArrayUtils.contains(typeArray, id))
                    .findAny()
                    .isPresent();
        }
    }

    public boolean isPriceNullOrBetween(int price) {
        return priceRange == null || priceRange.isInRange(price);
    }
}
