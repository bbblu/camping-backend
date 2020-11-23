package tw.edu.ntub.imd.camping.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tw.edu.ntub.birc.common.annotation.AliasName;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "租借紀錄", description = "租借紀錄")
@Data
@EqualsAndHashCode
public class RentalRecordBean {
    @Hidden
    @Null(message = "id - 不得填寫")
    private Integer id;

    @Schema(description = "租借商品編號", example = "1")
    @NotNull(message = "租借商品 - 未填寫")
    @Min(value = 1, message = "租借商品 - 應為大於等於{value}的數字")
    private Integer productGroupId;

    @Hidden
    @Getter(AccessLevel.NONE)
    @Null(message = "enable - 不得填寫")
    private Boolean enable;

    @Hidden
    @Null(message = "status - 不得填寫")
    private RentalRecordStatus status;

    @Hidden
    @Null(message = "renterAccount - 不得填寫")
    private String renterAccount;

    @Hidden
    @Null(message = "renter - 不得填寫")
    @AliasName("userByRenterAccount")
    private UserBean renter;

    @Hidden
    @Null(message = "rentalDate - 不得填寫")
    private LocalDateTime rentalDate;

    @Schema(description = "預計租借起始時間(yyyy/MM/dd HH:mm)", type = "string", example = "2020/08/16 15:28")
    @NotNull(message = "預借租借起始時間 - 未填寫")
    @FutureOrPresent(message = "預借租借起始時間 - 應選擇現在或未來時間")
    private LocalDateTime borrowStartDate;

    @Schema(description = "預借租借結束時間(yyyy/MM/dd HH:mm)", type = "string", example = "2020/08/18 10:00")
    @NotNull(message = "預借租借結束時間 - 未填寫")
    @FutureOrPresent(message = "預借租借結束時間 - 應選擇現在或未來時間")
    private LocalDateTime borrowEndDate;

    @Hidden
    @JsonIgnore
    @Null(message = "agreeDate - 不得填寫")
    private LocalDateTime agreeDate;

    @Hidden
    @JsonIgnore
    @Null(message = "paymentDate - 不得填寫")
    private LocalDateTime paymentDate;

    @Hidden
    @JsonIgnore
    @Null(message = "placedDate - 不得填寫")
    private LocalDateTime placedDate;

    @Hidden
    @JsonIgnore
    @Null(message = "pickDate - 不得填寫")
    private LocalDateTime pickDate;

    @Hidden
    @JsonIgnore
    @Null(message = "returnDate - 不得填寫")
    private LocalDateTime returnDate;

    @Hidden
    @JsonIgnore
    @Null(message = "backDate - 不得填寫")
    private LocalDateTime backDate;

    @Hidden
    @JsonIgnore
    @Null(message = "cancelDate - 不得填寫")
    private LocalDateTime cancelDate;

    @Hidden
    @JsonIgnore
    @Null(message = "productGroup - 不得填寫")
    private ProductGroupBean productGroup;

    @Hidden
    @JsonIgnore
    @Null(message = "detailBeanList - 不得填寫")
    private List<RentalDetailBean> detailBeanList;

    @Hidden
    public Boolean isEnable() {
        return enable;
    }

    @Hidden
    @AssertTrue(message = "預計租借起始日期應等於結束日期或在結束日期之前")
    private boolean isBorrowStartDateAfterOrEqualEndDate() {
        if (borrowStartDate != null && borrowEndDate != null) {
            return borrowStartDate.isEqual(borrowEndDate) || borrowStartDate.isBefore(borrowEndDate);
        } else {
            return true;
        }
    }

    @Hidden
    @AssertTrue(message = "預計租借起始日期應選擇七天以後的日期")
    private boolean isBorrowStartDateAfterCurrentDateSixDay() {
        if (borrowStartDate != null) {
            return borrowStartDate.toLocalDate().isAfter(LocalDate.now().plusDays(6));
        } else {
            return true;
        }
    }
}
