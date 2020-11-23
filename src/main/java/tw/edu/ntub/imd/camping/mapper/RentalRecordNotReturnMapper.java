package tw.edu.ntub.imd.camping.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.bean.RentalRecordProductBrokenBean;
import tw.edu.ntub.imd.camping.bean.RentalRecordProductStatusBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.RentalRecordCheckLogDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserBadRecordDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordCheckLog;
import tw.edu.ntub.imd.camping.databaseconfig.entity.UserBadRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserBadRecordType;
import tw.edu.ntub.imd.camping.exception.RentalRecordStatusException;

import java.util.EnumSet;

@RequiredArgsConstructor
@Component
public class RentalRecordNotReturnMapper implements RentalRecordStatusMapper {
    private static final EnumSet<RentalRecordStatus> CAN_CHANGE_TO_STATUS_SET = EnumSet.of(
            RentalRecordStatus.NOT_RETRIEVE
    );
    private final RentalRecordCheckLogDAO checkLogDAO;
    private final UserBadRecordDAO userBadRecordDAO;

    @Override
    public RentalRecordStatus getMappedStatus() {
        return RentalRecordStatus.NOT_RETURN;
    }

    @Override
    public EnumSet<RentalRecordStatus> getCanChangeToStatusSet() {
        return CAN_CHANGE_TO_STATUS_SET;
    }

    @Override
    public void validate(RentalRecord record, RentalRecordStatus newStatus) throws RentalRecordStatusException {

    }

    @Override
    public void beforeChange(RentalRecord record, RentalRecordStatus newStatus, Object payload) throws ClassCastException {

    }

    @Override
    public void afterChange(RentalRecord record, RentalRecordStatus originStatus, Object payload) throws ClassCastException {
        if (record.getStatus() == RentalRecordStatus.NOT_RETRIEVE) {
            if (payload instanceof RentalRecordProductBrokenBean) {
                saveBadRecord(record.getRenterAccount(), UserBadRecordType.BROKEN_PRODUCT);
                saveCheckLog(record, originStatus, ((RentalRecordProductBrokenBean) payload).getDescription());
            } else if (payload instanceof RentalRecordProductStatusBean) {
                saveCheckLog(record, originStatus, ((RentalRecordProductStatusBean) payload).getDescription());
            }
        }
    }

    private void saveBadRecord(String account, UserBadRecordType type) {
        UserBadRecord badRecord = new UserBadRecord();
        badRecord.setType(type);
        badRecord.setUserAccount(account);
        userBadRecordDAO.save(badRecord);
    }

    private void saveCheckLog(RentalRecord record, RentalRecordStatus originStatus, String logContent) {
        RentalRecordCheckLog checkLog = new RentalRecordCheckLog(record.getId(), originStatus, logContent);
        checkLogDAO.save(checkLog);
    }
}
