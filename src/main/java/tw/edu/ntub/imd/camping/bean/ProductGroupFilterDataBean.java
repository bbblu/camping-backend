package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.birc.common.util.ArrayUtils;
import tw.edu.ntub.imd.camping.enumerate.ProductGroupPriceRange;

import java.time.LocalDate;
import java.util.Arrays;

@Hidden
@Data
@EqualsAndHashCode
public class ProductGroupFilterDataBean {
    private LocalDate borrowStartDate;
    private LocalDate borrowEndDate;
    private Integer cityId;
    private int[] typeArray;
    private ProductGroupPriceRange priceRange;

    public boolean isBorrowStartDateNullOrBeforeOrEquals(LocalDate productGroupBorrowStartDate) {
        return this.borrowStartDate == null || this.borrowStartDate.isBefore(productGroupBorrowStartDate) || productGroupBorrowStartDate.isEqual(this.borrowStartDate);
    }

    public boolean isBorrowEndDateNullOrAfterOrEquals(LocalDate productGroupborrowEndDate) {
        return this.borrowEndDate == null || this.borrowEndDate.isAfter(productGroupborrowEndDate) || productGroupborrowEndDate.isEqual(this.borrowEndDate);
    }

    public boolean isCityIdNullOrEquals(Integer cityId) {
        return this.cityId == null || this.cityId.equals(cityId);
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
