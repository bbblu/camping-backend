package tw.edu.ntub.imd.camping.mapper;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.exception.RentalRecordStatusException;

import java.util.EnumSet;

@Component
public class RentalRecordNotAgreeMapper implements RentalRecordStatusMapper {
    private static final EnumSet<RentalRecordStatus> CAN_CHANGE_TO_STATUS_SET = EnumSet.of(
            RentalRecordStatus.NOT_PAY,
            RentalRecordStatus.ALREADY_CANCEL
    );

    @Override
    public RentalRecordStatus getMappedStatus() {
        return RentalRecordStatus.NOT_AGREE;
    }

    @Override
    public EnumSet<RentalRecordStatus> getCanChangeToStatusSet() {
        return CAN_CHANGE_TO_STATUS_SET;
    }

    @Override
    public void validate(RentalRecord record, RentalRecordStatus newStatus) throws RentalRecordStatusException {
        ProductGroup productGroup = record.getProductGroupByProductGroupId();
        String loginUserAccount = SecurityUtils.getLoginUserAccount();
        if (StringUtils.isNotEquals(productGroup.getCreateAccount(), loginUserAccount)) {
            throw new NotProductOwnerException();
        }
    }

    @Override
    public void beforeChange(RentalRecord record, RentalRecordStatus newStatus, Object payload) throws ClassCastException {

    }

    @Override
    public void afterChange(RentalRecord record, RentalRecordStatus originStatus, Object payload) throws ClassCastException {

    }

    private static class NotProductOwnerException extends RentalRecordStatusException {
        public NotProductOwnerException() {
            super("您並非此交易的出借方，無法同意或拒絕出借");
        }

        @Override
        public String getReason() {
            return "CouldNotAgreeOrDeniedRental";
        }
    }
}
