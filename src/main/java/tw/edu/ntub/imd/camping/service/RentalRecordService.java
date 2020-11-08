package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.RentalRecordBean;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import java.util.List;

public interface RentalRecordService extends BaseService<RentalRecordBean, Integer> {
    List<RentalRecordBean> searchByRenterAccount(String renterAccount);

    List<RentalRecordBean> searchByProductGroupCreateAccount(String productGroupCreateAccount);

    void updateStatusToNext(int id);

    void deniedTransaction(int id, String description);

    Integer requestCancelRecord(int id, String cancelDetail);

    void agreeCancel(int id);

    void deniedCancel(int id, String deniedDetail);

    void unexpectedStatusChange(int id, String description, RentalRecordStatus newStatus);

    String getChangeLogDescription(int id, RentalRecordStatus status);
}
