package tw.edu.ntub.imd.camping.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class CanBorrowProductGroupBean {
    private Integer id;
    private String coverImage;
    private Integer price;
    private LocalDateTime borrowStartDate;
    private LocalDateTime borrowEndDate;
    private String userName;
}
