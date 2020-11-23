package tw.edu.ntub.imd.camping.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.databaseconfig.dao.NotificationDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Notification;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.NotificationType;

import java.util.Date;

@AllArgsConstructor
@Component
public class NotificationUtils {
    private final NotificationDAO notificationDAO;

    public void create(RentalRecord rentalRecord) {
        switch (rentalRecord.getStatus()) {
            case ALREADY_CANCEL:
                createCancelNotification(rentalRecord);
                break;
            case NOT_AGREE:
                createRentalNotification(rentalRecord);
                break;
            case NOT_RETURN:
                createProductServiceNotification(rentalRecord);
                break;
            case NOT_RETRIEVE:
                createProductReturned(rentalRecord);
                break;
            case NOT_COMMENT:
                createCommentNotification(rentalRecord);
                break;
            default:
                break;
        }
    }

    private void createRentalNotification(RentalRecord rentalRecord) {
        Notification notification = new Notification();
        notification.setRentalRecordId(rentalRecord.getId());
        notification.setType(NotificationType.RENTAL);
        notification.setContent(NotificationType.RENTAL.getMessage());
        ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
        notification.setUserAccount(productGroup.getCreateAccount());
        notificationDAO.save(notification);
    }

    private void createCancelNotification(RentalRecord rentalRecord) {
        Notification renterNotification = new Notification();
        renterNotification.setType(NotificationType.CANCEL_RENTER);
        renterNotification.setRentalRecordId(rentalRecord.getId());
        renterNotification.setContent(NotificationType.CANCEL_RENTER.getMessage(rentalRecord.getId()));
        renterNotification.setUserAccount(rentalRecord.getRenterAccount());
        notificationDAO.save(renterNotification);

        Notification productOwnerNotification = new Notification();
        productOwnerNotification.setType(NotificationType.CANCEL_PRODUCT_OWNER);
        productOwnerNotification.setRentalRecordId(rentalRecord.getId());
        productOwnerNotification.setContent(NotificationType.CANCEL_PRODUCT_OWNER.getMessage(rentalRecord.getId()));
        ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
        productOwnerNotification.setUserAccount(productGroup.getCreateAccount());
        notificationDAO.save(productOwnerNotification);
    }

    private void createProductServiceNotification(RentalRecord rentalRecord) {
        Notification notification = new Notification();
        notification.setType(NotificationType.PRODUCT_SERVICE);
        notification.setRentalRecordId(rentalRecord.getId());
        Date borrowEndDate = DateUtils.convertLocalDateTimeToDate(rentalRecord.getBorrowEndDate());
        notification.setContent(NotificationType.PRODUCT_SERVICE.getMessage(
                rentalRecord.getId(),
                borrowEndDate,
                borrowEndDate,
                borrowEndDate
        ));
        notification.setUserAccount(rentalRecord.getRenterAccount());
        notificationDAO.save(notification);
    }

    private void createProductReturned(RentalRecord rentalRecord) {
        Notification notification = new Notification();
        notification.setType(NotificationType.PRODUCT_SERVICE);
        notification.setRentalRecordId(rentalRecord.getId());
        Date borrowEndDate = DateUtils.convertLocalDateTimeToDate(rentalRecord.getReturnDate().plusDays(7));
        notification.setContent(NotificationType.PRODUCT_SERVICE.getMessage(
                rentalRecord.getId(),
                borrowEndDate,
                borrowEndDate,
                borrowEndDate
        ));
        ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
        notification.setUserAccount(productGroup.getCreateAccount());
        notificationDAO.save(notification);
    }

    private void createCommentNotification(RentalRecord rentalRecord) {
        Notification renterNotification = new Notification();
        renterNotification.setType(NotificationType.COMMENT);
        renterNotification.setRentalRecordId(rentalRecord.getId());
        renterNotification.setContent(NotificationType.COMMENT.getMessage(rentalRecord.getId()));
        renterNotification.setUserAccount(rentalRecord.getRenterAccount());
        notificationDAO.save(renterNotification);

        Notification productOwnerNotification = new Notification();
        productOwnerNotification.setType(NotificationType.COMMENT);
        productOwnerNotification.setRentalRecordId(rentalRecord.getId());
        productOwnerNotification.setContent(NotificationType.COMMENT.getMessage(rentalRecord.getId()));
        ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
        productOwnerNotification.setUserAccount(productGroup.getCreateAccount());
        notificationDAO.save(productOwnerNotification);
    }
}
