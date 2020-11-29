package tw.edu.ntub.imd.camping.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserBadRecordDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.entity.UserBadRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserBadRecordType;
import tw.edu.ntub.imd.camping.exception.RentalRecordStatusException;

import java.util.EnumSet;

@RequiredArgsConstructor
@Component
public class RentalRecordNotPlaceMapper implements RentalRecordStatusMapper {
    private static final EnumSet<RentalRecordStatus> CAN_CHANGE_TO_STATUS_SET = EnumSet.of(
            RentalRecordStatus.NOT_PICK_UP,
            RentalRecordStatus.BE_RETURNED,
            RentalRecordStatus.ALREADY_CANCEL
    );
    private final UserBadRecordDAO userBadRecordDAO;

    @Override
    public RentalRecordStatus getMappedStatus() {
        return RentalRecordStatus.NOT_PLACE;
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
        if (isProductOwnerCancel(record)) {
            ProductGroup productGroup = record.getProductGroupByProductGroupId();
            saveBadRecord(productGroup.getCreateAccount(), UserBadRecordType.CANCEL_RECORD);
        } else if (record.getStatus() == RentalRecordStatus.BE_RETURNED) {
            saveBadRecord(record.getRenterAccount(), UserBadRecordType.BE_RETURN);
        }
    }

    private boolean isProductOwnerCancel(RentalRecord record) {
        ProductGroup productGroup = record.getProductGroupByProductGroupId();
        return record.getStatus() == RentalRecordStatus.ALREADY_CANCEL &&
                StringUtils.isEquals(productGroup.getCreateAccount(), SecurityUtils.getLoginUserAccount());
    }

    private void saveBadRecord(String account, UserBadRecordType type) {
        UserBadRecord badRecord = new UserBadRecord();
        badRecord.setType(type);
        badRecord.setUserAccount(account);
        userBadRecordDAO.save(badRecord);
    }
}
