package tw.edu.ntub.imd.camping.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.bean.RentalRecordProductBrokenBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserBadRecordDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserCompensateRecordDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.entity.UserBadRecord;
import tw.edu.ntub.imd.camping.databaseconfig.entity.UserCompensateRecord;
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
    private final UserBadRecordDAO userBadRecordDAO;
    private final UserCompensateRecordDAO userCompensateRecordDAO;

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
                RentalRecordProductBrokenBean brokenBean = (RentalRecordProductBrokenBean) payload;
                saveBadRecord(record.getRenterAccount(), UserBadRecordType.BROKEN_PRODUCT);

                UserCompensateRecord compensateRecord = new UserCompensateRecord();
                compensateRecord.setUserAccount(record.getRenterAccount());
                compensateRecord.setRentalRecordId(record.getId());
                compensateRecord.setCompensatePrice(brokenBean.getCompensatePrice());
                userCompensateRecordDAO.save(compensateRecord);
            }
        }
    }

    private void saveBadRecord(String account, UserBadRecordType type) {
        UserBadRecord badRecord = new UserBadRecord();
        badRecord.setType(type);
        badRecord.setUserAccount(account);
        userBadRecordDAO.save(badRecord);
    }
}
