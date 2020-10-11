package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.RentalRecordBean;

import java.util.List;

public interface RentalRecordService extends BaseService<RentalRecordBean, Integer> {
    List<RentalRecordBean> searchByRenterAccount(String renterAccount);

    List<RentalRecordBean> searchByProductGroupCreateAccount(String productGroupCreateAccount);

    void updateStatusToNext(int id);

    Integer requestCancelRecord(int id, String cancelDetail);

    void agreeCancel(int id);

    void deniedCancel(int id, String deniedDetail);
}
