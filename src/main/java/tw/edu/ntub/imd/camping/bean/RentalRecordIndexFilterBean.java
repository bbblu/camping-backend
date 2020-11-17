package tw.edu.ntub.imd.camping.bean;

import lombok.Data;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import java.time.LocalDate;

@Data
public class RentalRecordIndexFilterBean {
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private RentalRecordStatus status;

    public boolean isAfterOrEqualsStartDate(LocalDate dateTime) {
        return dateTime.isAfter(rentalStartDate) || dateTime.isEqual(rentalStartDate);
    }

    public boolean isBeforeOrEqualsStartDate(LocalDate dateTime) {
        return dateTime.isBefore(rentalEndDate) || dateTime.isEqual(rentalEndDate);
    }

    public boolean isBetweenStartDateAndEndDate(LocalDate dateTime) {
        return isAfterOrEqualsStartDate(dateTime) && isBeforeOrEqualsStartDate(dateTime);
    }
}
