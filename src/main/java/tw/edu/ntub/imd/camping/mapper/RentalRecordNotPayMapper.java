package tw.edu.ntub.imd.camping.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.NotificationDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Notification;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.NotificationType;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.dto.CreditCard;
import tw.edu.ntub.imd.camping.exception.RentalRecordStatusException;
import tw.edu.ntub.imd.camping.util.TransactionUtils;

import java.util.EnumSet;

@RequiredArgsConstructor
@Component
public class RentalRecordNotPayMapper implements RentalRecordStatusMapper {
    private static final EnumSet<RentalRecordStatus> CAN_CHANGE_TO_STATUS_SET = EnumSet.of(
            RentalRecordStatus.NOT_PLACE,
            RentalRecordStatus.ALREADY_CANCEL
    );
    private final TransactionUtils transactionUtils;
    private final NotificationDAO notificationDAO;

    @Override
    public RentalRecordStatus getMappedStatus() {
        return RentalRecordStatus.NOT_PAY;
    }

    @Override
    public EnumSet<RentalRecordStatus> getCanChangeToStatusSet() {
        return CAN_CHANGE_TO_STATUS_SET;
    }

    @Override
    public void validate(RentalRecord record, RentalRecordStatus newStatus) throws RentalRecordStatusException {
        String loginUserAccount = SecurityUtils.getLoginUserAccount();
        boolean isNotRenter = StringUtils.isNotEquals(record.getRenterAccount(), loginUserAccount);
        if (newStatus == RentalRecordStatus.NOT_PLACE && isNotRenter) {
            throw new NotRenterException();
        }
    }

    @Override
    public void beforeChange(RentalRecord record, RentalRecordStatus newStatus, Object payload) throws ClassCastException {
        if (newStatus == RentalRecordStatus.NOT_PLACE) {
            try {
                CreditCard creditCard = (CreditCard) payload;
                ProductGroup productGroup = record.getProductGroupByProductGroupId();
                transactionUtils.createTransaction(creditCard, productGroup.getBankAccount(), productGroup.getPrice());
            } catch (RuntimeException e) {
                Notification notification = new Notification();
                notification.setRentalRecordId(record.getId());
                notification.setType(NotificationType.PAYMENT_FAIL);
                notificationDAO.save(notification);
                throw e;
            }
        }
    }

    @Override
    public void afterChange(RentalRecord record, RentalRecordStatus originStatus, Object payload) throws ClassCastException {
        if (record.getStatus() == RentalRecordStatus.NOT_PLACE) {
            Notification notification = new Notification();
            notification.setRentalRecordId(record.getId());
            notification.setType(NotificationType.PAYMENT_SUCCESS);
            notification.setUserAccount(SecurityUtils.getLoginUserAccount());
            notification.setContent(NotificationType.PAYMENT_SUCCESS.getMessage(record.getId()));
            notificationDAO.save(notification);
        }
    }

    private static class NotRenterException extends RentalRecordStatusException {
        public NotRenterException() {
            super("您並非此交易的租借方，無法付款");
        }

        @Override
        public String getReason() {
            return "NotRenter";
        }
    }
}
