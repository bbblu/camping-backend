package tw.edu.ntub.imd.camping.schedule;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.bean.RentalRecordProductStatusBean;
import tw.edu.ntub.imd.camping.bean.RentalRecordStatusChangeBean;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.service.RentalRecordService;

import java.time.LocalDate;

@AllArgsConstructor
@Component
public class RentalRecordScheduler {
    private final RentalRecordService rentalRecordService;

    @Scheduled(cron = "0 1 1 * * ?")
    public void checkRentalRecordNotPayDate() {
        rentalRecordService.searchByStatus(RentalRecordStatus.NOT_PAY)
                .stream()
                .filter(record -> record.getAgreeDate().toLocalDate().plusDays(1).isBefore(LocalDate.now()))
                .map(record -> RentalRecordStatusChangeBean.builder()
                        .id(record.getId())
                        .changeDescription("超過應付款時間，視作取消交易")
                        .newStatus(RentalRecordStatus.ALREADY_CANCEL)
                        .build()
                )
                .forEach(rentalRecordService::updateStatus);
    }

    @Scheduled(cron = "0 1 1 * * ?")
    public void checkRentalRecordNotPickUpDateAndBorrowEndDate() {
        rentalRecordService.searchByStatus(RentalRecordStatus.NOT_PICK_UP)
                .stream()
                .filter(record -> record.getBorrowEndDate().toLocalDate().isBefore(LocalDate.now()))
                .map(record -> RentalRecordStatusChangeBean.builder()
                        .id(record.getId())
                        .changeDescription("超過租借結束時間，視作已歸還")
                        .newStatus(RentalRecordStatus.NOT_RETRIEVE)
                        .payload(new RentalRecordProductStatusBean("超過租借結束時間，與送達時一致"))
                        .build()
                )
                .forEach(rentalRecordService::updateStatus);
    }
}
