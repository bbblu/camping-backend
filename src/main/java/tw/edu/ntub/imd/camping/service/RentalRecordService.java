package tw.edu.ntub.imd.camping.service;

import org.springframework.lang.NonNull;
import tw.edu.ntub.imd.camping.bean.*;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import java.util.List;

public interface RentalRecordService extends BaseService<RentalRecordBean, Integer> {
    List<RentalRecordBean> searchByRenterAccount(String renterAccount);

    List<RentalRecordBean> searchByProductGroupCreateAccount(String productGroupCreateAccount);

    String getChangeLogDescription(int id, RentalRecordStatus status);

    default List<RentalRecordIndexBean> searchIndexBean() {
        return searchIndexBean(new RentalRecordIndexFilterBean());
    }

    List<RentalRecordIndexBean> searchIndexBean(@NonNull RentalRecordIndexFilterBean filterBean);

    void updateStatus(RentalRecordStatusChangeBean statusChangeBean);

    void saveComment(int id, int comment);

    List<RentalRecordBean> searchByStatus(RentalRecordStatus status);

    void saveProductStatus(int id, RentalRecordCheckLogBean productStatusBean);

    List<RentalRecordCheckLogBean> searchCheckLog(int id);

    String getTerminateDescription(int id);

    boolean isComment(Integer id, String commentAccount);
}
