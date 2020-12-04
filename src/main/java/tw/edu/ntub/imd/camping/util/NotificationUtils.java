package tw.edu.ntub.imd.camping.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.NotificationDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Notification;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.NotificationType;
import tw.edu.ntub.imd.camping.dto.Mail;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Component
public class NotificationUtils {
    private final NotificationDAO notificationDAO;
    private final MailSender mailSender;

    public void create(RentalRecord rentalRecord) {
        switch (rentalRecord.getStatus()) {
            case ALREADY_CANCEL:
                createCancelNotification(rentalRecord);
                break;
            case BE_RETURNED:
                createBeReturnedNotification(rentalRecord);
                break;
            case NOT_AGREE:
                createRentalNotification(rentalRecord);
                break;
            case NOT_PLACE:
                createAlreadyPayNotification(rentalRecord);
                break;
            case NOT_RETURN:
                createPlacedNotification(rentalRecord);
                break;
            case NOT_RETRIEVE:
                createAlreadyReturnNotification(rentalRecord);
                break;
            default:
                break;
        }
    }

    private void createCancelNotification(RentalRecord rentalRecord) {
        Notification renterNotification = new Notification();
        renterNotification.setType(NotificationType.CANCEL_SUCCESS);
        renterNotification.setRentalRecordId(rentalRecord.getId());
        renterNotification.setContent(NotificationType.CANCEL_SUCCESS.getMessage(rentalRecord.getId()));
        renterNotification.setUserAccount(SecurityUtils.getLoginUserAccount());
        notificationDAO.save(renterNotification);

        Notification productOwnerNotification = new Notification();
        productOwnerNotification.setType(NotificationType.ALREADY_CANCEL);
        productOwnerNotification.setRentalRecordId(rentalRecord.getId());
        productOwnerNotification.setContent(NotificationType.ALREADY_CANCEL.getMessage(rentalRecord.getId()));
        String userAccount;
        if (StringUtils.isEquals(rentalRecord.getRenterAccount(), SecurityUtils.getLoginUserAccount())) {
            ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
            userAccount = productGroup.getCreateAccount();
        } else {
            userAccount = rentalRecord.getRenterAccount();
        }
        productOwnerNotification.setUserAccount(userAccount);
        notificationDAO.save(productOwnerNotification);
        sendMail(productOwnerNotification);
    }

    private void createBeReturnedNotification(RentalRecord rentalRecord) {
        ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
        Notification renterNotification = new Notification();
        renterNotification.setType(NotificationType.BE_RETURN);
        renterNotification.setRentalRecordId(rentalRecord.getId());
        renterNotification.setContent(NotificationType.BE_RETURN.getMessage(rentalRecord.getId(), 3));
        renterNotification.setUserAccount(productGroup.getCreateAccount());
        notificationDAO.save(renterNotification);
        sendMail(renterNotification);
    }

    private void createRentalNotification(RentalRecord rentalRecord) {
        Notification notification = new Notification();
        notification.setRentalRecordId(rentalRecord.getId());
        notification.setType(NotificationType.RENTAL);
        notification.setContent(NotificationType.RENTAL.getMessage());
        ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
        notification.setUserAccount(productGroup.getCreateAccount());
        notificationDAO.save(notification);
        sendMail(notification);
    }

    public void sendMail(Notification notification) {
        Mail mail = new Mail("/mail/notification/message");
        mail.setSubject("借借露 - " + notification.getType());
        mail.addSendTo(notification.getUserAccount());
        mail.addAttribute("notification", notification);
        mailSender.sendMail(mail);
    }

    private void createAlreadyPayNotification(RentalRecord rentalRecord) {
        Notification notification = new Notification();
        notification.setRentalRecordId(rentalRecord.getId());
        notification.setType(NotificationType.ALREADY_PAY);
        LocalDateTime borrowStartDate = rentalRecord.getBorrowStartDate();
        LocalDateTime beforeBorrowStartDateOneDay = borrowStartDate.minusDays(1);
        Date date = DateUtils.convertLocalDateTimeToDate(beforeBorrowStartDateOneDay);
        notification.setContent(NotificationType.ALREADY_PAY.getMessage(rentalRecord.getId(), date, date));
        ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
        notification.setUserAccount(productGroup.getCreateAccount());
        notificationDAO.save(notification);
    }

    private void createPlacedNotification(RentalRecord rentalRecord) {
        Notification notification = new Notification();
        notification.setType(NotificationType.PLACED);
        notification.setRentalRecordId(rentalRecord.getId());
        Date borrowEndDate = DateUtils.convertLocalDateTimeToDate(rentalRecord.getBorrowEndDate());
        notification.setContent(NotificationType.PLACED.getMessage(
                rentalRecord.getId(),
                borrowEndDate,
                borrowEndDate,
                borrowEndDate
        ));
        notification.setUserAccount(rentalRecord.getRenterAccount());
        notificationDAO.save(notification);
        sendMail(notification);
    }

    private void createAlreadyReturnNotification(RentalRecord rentalRecord) {
        Notification notification = new Notification();
        notification.setType(NotificationType.ALREADY_RETURN);
        notification.setRentalRecordId(rentalRecord.getId());
        Date shouldRetrieveDate = DateUtils.convertLocalDateTimeToDate(rentalRecord.getReturnDate().plusDays(3));
        notification.setContent(NotificationType.ALREADY_RETURN.getMessage(
                rentalRecord.getId(),
                shouldRetrieveDate,
                shouldRetrieveDate,
                shouldRetrieveDate
        ));
        ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
        notification.setUserAccount(productGroup.getCreateAccount());
        notificationDAO.save(notification);
        sendMail(notification);
    }
}
