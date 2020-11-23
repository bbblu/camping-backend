package tw.edu.ntub.imd.camping.bean;

import lombok.Data;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import java.time.LocalDateTime;

@Data
public class RentalRecordIndexBean {
    private Integer id;
    private String idString;
    private RentalRecordStatus status;
    private LocalDateTime rentalDate;
    private String lastChangeStatusDescription;
}
