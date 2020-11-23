package tw.edu.ntub.imd.camping.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalRecordStatusChangeBean {
    private int id;
    private RentalRecordStatus newStatus;
    private String changeDescription;
    private Object payload;
}
