package tw.edu.ntub.imd.camping.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.RentalRecordProductStatusBean;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.RentalRecordCheckLogDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserBadRecordDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordCheckLog;
import tw.edu.ntub.imd.camping.databaseconfig.entity.UserBadRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserBadRecordType;
import tw.edu.ntub.imd.camping.exception.RentalRecordStatusException;

import java.time.LocalDate;
import java.util.EnumSet;

@RequiredArgsConstructor
@Component
public class RentalRecordNotPickUpMapper implements RentalRecordStatusMapper {
    private static final EnumSet<RentalRecordStatus> CAN_CHANGE_TO_STATUS_SET = EnumSet.of(
            RentalRecordStatus.NOT_RETURN,
            RentalRecordStatus.NOT_RETRIEVE,
            RentalRecordStatus.TERMINATE
    );
    private final RentalRecordCheckLogDAO checkLogDAO;
    private final UserBadRecordDAO userBadRecordDAO;

    @Override
    public RentalRecordStatus getMappedStatus() {
        return RentalRecordStatus.NOT_PICK_UP;
    }

    @Override
    public EnumSet<RentalRecordStatus> getCanChangeToStatusSet() {
        return CAN_CHANGE_TO_STATUS_SET;
    }

    @Override
    public void validate(RentalRecord record, RentalRecordStatus newStatus) throws RentalRecordStatusException {
        if (newStatus == RentalRecordStatus.NOT_RETRIEVE &&
                record.getBorrowEndDate().toLocalDate().isAfter(LocalDate.now())) {
            throw new NotAfterBorrowEndDateException();
        }
    }

    @Override
    public void beforeChange(RentalRecord record, RentalRecordStatus newStatus, Object payload) throws ClassCastException {

    }

    @Override
    public void afterChange(RentalRecord record, RentalRecordStatus originStatus, Object payload) throws ClassCastException {
        if (record.getStatus() == RentalRecordStatus.NOT_RETURN) {
            saveCheckLog(record, originStatus, (RentalRecordProductStatusBean) payload);
        } else if (isProductOwnerTerminate(record)) {
            ProductGroup productGroup = record.getProductGroupByProductGroupId();
            saveBadRecord(productGroup.getCreateAccount(), UserBadRecordType.CANCEL_RECORD);
        } else if (isRenterTerminate(record)) {
            saveBadRecord(record.getRenterAccount(), UserBadRecordType.TERMINATE_RECORD);
        }
    }

    private void saveCheckLog(RentalRecord record, RentalRecordStatus originStatus, RentalRecordProductStatusBean productStatus) {
        RentalRecordCheckLog checkLog = new RentalRecordCheckLog(record.getId(), originStatus, productStatus.getDescription());
        checkLogDAO.save(checkLog);
    }

    private boolean isProductOwnerTerminate(RentalRecord record) {
        ProductGroup productGroup = record.getProductGroupByProductGroupId();
        return record.getStatus() == RentalRecordStatus.TERMINATE &&
                StringUtils.isEquals(productGroup.getCreateAccount(), SecurityUtils.getLoginUserAccount());
    }

    private void saveBadRecord(String account, UserBadRecordType type) {
        UserBadRecord badRecord = new UserBadRecord();
        badRecord.setType(type);
        badRecord.setUserAccount(account);
        userBadRecordDAO.save(badRecord);
    }

    private boolean isRenterTerminate(RentalRecord record) {
        return record.getStatus() == RentalRecordStatus.TERMINATE &&
                StringUtils.isEquals(record.getRenterAccount(), SecurityUtils.getLoginUserAccount());
    }

    private static class NotAfterBorrowEndDateException extends RentalRecordStatusException {

        @Override
        public String getReason() {
            return "NowIsNotAfterBorrowEndDate";
        }
    }
}
