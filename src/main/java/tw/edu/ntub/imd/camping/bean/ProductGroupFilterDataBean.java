package tw.edu.ntub.imd.camping.bean;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.birc.common.util.ArrayUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.CanBorrowProductGroup;
import tw.edu.ntub.imd.camping.enumerate.ProductGroupPriceRange;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public boolean isBorrowStartDateNullOrBeforeOrEquals(CanBorrowProductGroup productGroup) {
        if (this.borrowStartDate == null) {
            return true;
        }
        LocalDateTime startDateTime = productGroup.getBorrowStartDate();
        LocalDate startDate = startDateTime.toLocalDate();
        return this.borrowStartDate.isAfter(startDate) || this.borrowStartDate.isEqual(startDate);
    }

    public boolean isBorrowEndDateNullOrAfterOrEquals(CanBorrowProductGroup productGroup) {
        if (this.borrowEndDate == null) {
            return true;
        }
        LocalDateTime endDateTime = productGroup.getBorrowEndDate();
        LocalDate endDate = endDateTime.toLocalDate();
        return this.borrowEndDate.isBefore(endDate) || endDate.isEqual(this.borrowEndDate);
    }

    public boolean isBorrowDateBetween(CanBorrowProductGroup productGroup) {
        if (this.borrowStartDate == null && this.borrowEndDate == null) {
            return true;
        }
        LocalDateTime startDateTime = productGroup.getBorrowStartDate();
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDateTime endDateTime = productGroup.getBorrowEndDate();
        LocalDate endDate = endDateTime.toLocalDate();
        return (startDate.isBefore(this.borrowStartDate) || startDate.isEqual(this.borrowStartDate)) &&
                (endDate.isAfter(this.borrowEndDate) || endDate.isEqual(this.borrowEndDate));
    }

    public boolean isCityIdNullOrEquals(CanBorrowProductGroup productGroup) {
        return this.cityId == null || this.cityId.equals(productGroup.getCityId());
    }

    public boolean isTypeArrayNullOrAllMatchContains(CanBorrowProductGroup productGroup) {
        if (this.typeArray == null) {
            return true;
        } else {
            String type = productGroup.getProductType();
            return Arrays.stream(type.split(","))
                    .mapToInt(Integer::parseInt)
                    .filter(id -> ArrayUtils.contains(typeArray, id))
                    .findAny()
                    .isPresent();
        }
    }

    public boolean isPriceNullOrBetween(CanBorrowProductGroup productGroup) {
        return priceRange == null || priceRange.isInRange(productGroup.getPrice());
    }
}
