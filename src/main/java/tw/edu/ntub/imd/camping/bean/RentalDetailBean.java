package tw.edu.ntub.imd.camping.bean;

import lombok.Data;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalDetailStatus;

import java.time.LocalDateTime;

@Data
public class RentalDetailBean {
    private Integer id;
    private Integer recordId;
    private Integer productId;
    private RentalDetailStatus status;
    private String checkMemo;
    private String lastModifyAccount;
    private LocalDateTime lastModifyDate;
    private ProductBean product;
}
