package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ntub.birc.common.util.MathUtils;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.RentalRecordBean;
import tw.edu.ntub.imd.camping.bean.RentalRecordIndexBean;
import tw.edu.ntub.imd.camping.bean.RentalRecordIndexFilterBean;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.*;
import tw.edu.ntub.imd.camping.databaseconfig.entity.*;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.NotificationType;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordCancelStatus;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.dto.CreditCard;
import tw.edu.ntub.imd.camping.exception.*;
import tw.edu.ntub.imd.camping.service.RentalRecordService;
import tw.edu.ntub.imd.camping.service.transformer.RentalDetailTransformer;
import tw.edu.ntub.imd.camping.service.transformer.RentalRecordIndexTransformer;
import tw.edu.ntub.imd.camping.service.transformer.RentalRecordTransformer;
import tw.edu.ntub.imd.camping.util.DateUtils;
import tw.edu.ntub.imd.camping.util.OwnerChecker;
import tw.edu.ntub.imd.camping.util.TransactionUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalRecordServiceImpl extends BaseServiceImpl<RentalRecordBean, RentalRecord, Integer> implements RentalRecordService {
    private final RentalRecordDAO recordDAO;
    private final RentalRecordTransformer transformer;
    private final RentalDetailDAO detailDAO;
    private final RentalDetailTransformer detailTransformer;
    private final ProductGroupDAO productGroupDAO;
    private final ProductDAO productDAO;
    private final CanBorrowProductGroupDAO canBorrowProductGroupDAO;
    private final TransactionUtils transactionUtils;
    private final RentalRecordCancelDAO cancelDAO;
    private final RentalRecordStatusChangeLogDAO statusChangeLogDAO;
    private final UserDAO userDAO;
    private final UserCommentDAO userCommentDAO;
    private final RentalRecordIndexTransformer indexTransformer;
    private final RentalRecordCheckLogDAO checkLogDAO;
    private final NotificationDAO notificationDAO;

    public RentalRecordServiceImpl(
            RentalRecordDAO recordDAO,
            RentalRecordTransformer transformer,
            RentalDetailDAO detailDAO,
            RentalDetailTransformer detailTransformer,
            ProductGroupDAO productGroupDAO,
            ProductDAO productDAO,
            CanBorrowProductGroupDAO canBorrowProductGroupDAO,
            TransactionUtils transactionUtils,
            RentalRecordCancelDAO cancelDAO,
            RentalRecordStatusChangeLogDAO statusChangeLogDAO,
            UserDAO userDAO,
            UserCommentDAO userCommentDAO,
            RentalRecordIndexTransformer indexTransformer,
            RentalRecordCheckLogDAO checkLogDAO,
            NotificationDAO notificationDAO) {
        super(recordDAO, transformer);
        this.recordDAO = recordDAO;
        this.transformer = transformer;
        this.detailDAO = detailDAO;
        this.detailTransformer = detailTransformer;
        this.productGroupDAO = productGroupDAO;
        this.productDAO = productDAO;
        this.canBorrowProductGroupDAO = canBorrowProductGroupDAO;
        this.transactionUtils = transactionUtils;
        this.cancelDAO = cancelDAO;
        this.statusChangeLogDAO = statusChangeLogDAO;
        this.userDAO = userDAO;
        this.userCommentDAO = userCommentDAO;
        this.indexTransformer = indexTransformer;
        this.checkLogDAO = checkLogDAO;
        this.notificationDAO = notificationDAO;
    }

    @Override
    public RentalRecordBean save(RentalRecordBean rentalRecordBean) {
        OwnerChecker.checkCanBorrowProductGroup(canBorrowProductGroupDAO, rentalRecordBean.getProductGroupId());
        if (userDAO.isLocked(SecurityUtils.getLoginUserAccount())) {
            throw new LockedUserException();
        }

        RentalRecord rentalRecord = transformer.transferToEntity(rentalRecordBean);
        RentalRecord saveResult = recordDAO.saveAndFlush(rentalRecord);
        saveDetail(saveResult.getId(), saveResult.getProductGroupId());
        ProductGroup productGroup = productGroupDAO.findById(rentalRecord.getProductGroupId()).orElseThrow();
        saveNotification(saveResult.getId(), productGroup.getCreateAccount(), NotificationType.RENTAL);
        return transformer.transferToBean(saveResult);
    }

    private void saveDetail(int recordId, int productGroupId) {
        detailDAO.saveAll(productDAO.findByGroupId(productGroupId)
                .parallelStream()
                .map(detailTransformer::transferProductToEntity)
                .peek(rentalDetail -> rentalDetail.setRecordId(recordId))
                .collect(Collectors.toList())
        );
    }

    private void saveNotification(Integer recordId, String notifyAccount, NotificationType notificationType, Object... messageArgs) {
        Notification notification = new Notification();
        notification.setRentalRecordId(recordId);
        notification.setType(notificationType);
        notification.setUserAccount(notifyAccount);
        notification.setContent(notificationType.getMessage(messageArgs));
        notificationDAO.save(notification);
    }

    @Override
    public List<RentalRecordBean> searchByRenterAccount(String renterAccount) {
        return transformer.transferToBeanList(recordDAO.findByRenterAccountAndEnableIsTrue(renterAccount, Sort.by(Sort.Order.desc(RentalRecord_.RENTAL_DATE))));
    }

    @Override
    public List<RentalRecordBean> searchByProductGroupCreateAccount(String productGroupCreateAccount) {
        return transformer.transferToBeanList(recordDAO.findAllBorrowRecord(productGroupCreateAccount, Sort.by(Sort.Order.desc(RentalRecord_.RENTAL_DATE))));
    }

    @Override
    public void payment(int id, CreditCard renterCreditCard) {
        RentalRecord rentalRecord = recordDAO.findById(id).orElseThrow(() -> new NotFoundException("無此租借紀錄"));
        if (StringUtils.isNotEquals(rentalRecord.getRenterAccount(), SecurityUtils.getLoginUserAccount())) {
            throw new NotRentalRecordRenterException(id, SecurityUtils.getLoginUserAccount());
        }
        try {
            ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
            int transactionId = transactionUtils.createTransaction(renterCreditCard, productGroup.getBankAccount(), productGroup.getPrice());
            rentalRecord.setTransactionId(transactionId);
            String creditCardId = renterCreditCard.getCardId();
            rentalRecord.setRenterCreditCardId("*".repeat(12).concat(StringUtils.mid(creditCardId, -4)));
            saveStatusChangeLog(rentalRecord, RentalRecordStatus.NOT_PLACED, "已付款");
            rentalRecord.setStatus(RentalRecordStatus.NOT_PLACED);
            recordDAO.update(rentalRecord);
            saveNotification(rentalRecord.getId(), rentalRecord.getRenterAccount(), NotificationType.PAYMENT_SUCCESS, rentalRecord.getId());
        } catch (RuntimeException e) {
            saveNotification(rentalRecord.getId(), rentalRecord.getRenterAccount(), NotificationType.PAYMENT_FAIL, rentalRecord.getId());
            throw e;
        }
    }

    @Override
    public void createCheckLog(Integer id, String content) {
        RentalRecordCheckLog checkLog = new RentalRecordCheckLog(id, content);
        checkLogDAO.save(checkLog);
    }

    @Override
    public RentalRecordStatus updateStatusToNext(int id) {
        RentalRecord rentalRecord = getUpdateRentalRecord(id);
        RentalRecordStatus status = rentalRecord.getStatus();
        saveStatusChangeLog(rentalRecord, status.next(), "原先狀態已完成");
        rentalRecord.setStatus(status.next());
        recordDAO.saveAndFlush(rentalRecord);
        switch (rentalRecord.getStatus()) {
            case NOT_PICK_UP:
                Date borrowEndDate = DateUtils.convertLocalDateTimeToDate(rentalRecord.getBorrowEndDate());
                saveNotification(
                        rentalRecord.getId(),
                        rentalRecord.getRenterAccount(),
                        NotificationType.PRODUCT_SERVICE,
                        rentalRecord.getId(),
                        borrowEndDate,
                        borrowEndDate,
                        borrowEndDate
                );
                break;
            case NOT_RETRIEVE:
                ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
                Date returnDate = DateUtils.convertLocalDateTimeToDate(rentalRecord.getReturnDate().plusDays(7));
                saveNotification(
                        rentalRecord.getId(),
                        productGroup.getCreateAccount(),
                        NotificationType.PRODUCT_RETURNED,
                        rentalRecord.getId(),
                        returnDate,
                        returnDate,
                        returnDate
                );
                break;
        }
        return rentalRecord.getStatus();
    }

    @Override
    @Transactional
    public void deniedTransaction(int id, String description) {
        RentalRecord rentalRecord = recordDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("無此紀錄：" + id));
        RentalRecordStatus status = rentalRecord.getStatus();
        if (status.isCanChangeTo(RentalRecordStatus.CANCEL)) {
            try {
                OwnerChecker.checkIsProductGroupOwner(productGroupDAO, rentalRecord.getProductGroupId());
                RentalRecordCancel rentalRecordCancel = new RentalRecordCancel();
                rentalRecordCancel.setStatus(RentalRecordCancelStatus.CANCEL_SUCCESS);
                rentalRecordCancel.setRecordId(id);
                rentalRecordCancel.setProductOwnerAgreeDate(LocalDateTime.now());
                rentalRecordCancel.setRenterAgreeDate(LocalDateTime.now());
                rentalRecordCancel.setCancelDetail(description);
                cancelDAO.save(rentalRecordCancel);
                saveStatusChangeLog(rentalRecord, RentalRecordStatus.CANCEL, description);
                rentalRecord.setStatus(RentalRecordStatus.CANCEL);
                recordDAO.update(rentalRecord);
            } catch (NotFoundException e) {
                throw new NotProductGroupOwnerException(rentalRecord.getProductGroupId(), SecurityUtils.getLoginUserAccount());
            }
        } else {
            throw new ChangeRentalRecordStatusFailException(status, RentalRecordStatus.CANCEL);
        }
    }

    private RentalRecord getUpdateRentalRecord(int id) {
        Optional<RentalRecord> optionalRentalRecord = recordDAO.findById(id);
        RentalRecord rentalRecord = optionalRentalRecord.orElseThrow(() -> new NotFoundException("無此紀錄：" + id));
        if (SecurityUtils.isNotManager() && recordDAO.isNotRenterAndProductGroupCreator(id, SecurityUtils.getLoginUserAccount())) {
            throw new NotRentalRecordOwnerException(id, SecurityUtils.getLoginUserAccount());
        }
        RentalRecordStatus status = rentalRecord.getStatus();
        if (status == RentalRecordStatus.CANCEL) {
            throw new CanceledRentalRecordException(id);
        }
        return rentalRecord;
    }

    @Override
    @Transactional
    public Integer requestCancelRecord(int id, String cancelDetail) {
        Optional<RentalRecord> optionalRentalRecord = recordDAO.findById(id);
        RentalRecord rentalRecord = optionalRentalRecord.orElseThrow(() -> new NotFoundException("沒有此租借紀錄：" + id));
        if (rentalRecord.getStatus().isCanChangeTo(RentalRecordStatus.APPLY_CANCEL)) {
            saveStatusChangeLog(rentalRecord, RentalRecordStatus.APPLY_CANCEL, cancelDetail);
            rentalRecord.setStatus(RentalRecordStatus.APPLY_CANCEL);
            recordDAO.update(rentalRecord);

            RentalRecordCancel rentalRecordCancel = new RentalRecordCancel();
            rentalRecordCancel.setRecordId(id);
            rentalRecordCancel.setCancelDetail(cancelDetail);
            String loginUserAccount = SecurityUtils.getLoginUserAccount();
            if (StringUtils.isEquals(loginUserAccount, rentalRecord.getRenterAccount())) {
                rentalRecordCancel.setStatus(RentalRecordCancelStatus.WAIT_PRODUCT_OWNER_AGREE);
                rentalRecordCancel.setRenterAgreeDate(LocalDateTime.now());
            } else {
                rentalRecordCancel.setStatus(RentalRecordCancelStatus.WAIT_RENTER_AGREE);
                rentalRecordCancel.setProductOwnerAgreeDate(LocalDateTime.now());
            }
            RentalRecordCancel saveResult = cancelDAO.saveAndFlush(rentalRecordCancel);
            return saveResult.getId();
        } else {
            throw new RequestCancelRentalRecordFailException(rentalRecord.getStatus());
        }
    }

    @Override
    @Transactional
    public void agreeCancel(int id) {
        Optional<RentalRecordCancel> optionalRentalRecordCancel = cancelDAO.findWaitResponseRecord(id);
        optionalRentalRecordCancel.ifPresent(rentalRecordCancel -> {
            rentalRecordCancel.setStatus(RentalRecordCancelStatus.CANCEL_SUCCESS);
            if (rentalRecordCancel.getRenterAgreeDate() != null) {
                rentalRecordCancel.setProductOwnerAgreeDate(LocalDateTime.now());
            } else {
                rentalRecordCancel.setRenterAgreeDate(LocalDateTime.now());
            }
            cancelDAO.save(rentalRecordCancel);
            RentalRecord rentalRecord = rentalRecordCancel.getRentalRecord();
            saveStatusChangeLog(rentalRecord, RentalRecordStatus.CANCEL, rentalRecordCancel.getCancelDetail());
            rentalRecord.setStatus(RentalRecordStatus.CANCEL);
            recordDAO.save(rentalRecord);
            saveNotification(rentalRecordCancel.getRecordId(), rentalRecordCancel.getCreateAccount(), NotificationType.CANCEL_SUCCESS, rentalRecord.getId());
        });
    }

    private void saveStatusChangeLog(RentalRecord record, RentalRecordStatus newStatus, String description) {
        RentalRecordStatusChangeLog log = new RentalRecordStatusChangeLog();
        log.setRecordId(record.getId());
        log.setFromStatus(record.getStatus());
        log.setToStatus(newStatus);
        log.setDescription(description);
        statusChangeLogDAO.save(log);
    }

    @Override
    @Transactional
    public void deniedCancel(int id, String deniedDetail) {
        Optional<RentalRecordCancel> optionalRentalRecordCancel = cancelDAO.findWaitResponseRecord(id);
        optionalRentalRecordCancel.ifPresent(rentalRecordCancel -> {
            rentalRecordCancel.setStatus(RentalRecordCancelStatus.CANCEL_DENIED);
            rentalRecordCancel.setDeniedDetail(deniedDetail);
            cancelDAO.save(rentalRecordCancel);
            RentalRecord rentalRecord = rentalRecordCancel.getRentalRecord();
            saveStatusChangeLog(rentalRecord, RentalRecordStatus.NOT_PLACED, deniedDetail);
            rentalRecord.setStatus(RentalRecordStatus.NOT_PLACED);
            recordDAO.update(rentalRecord);
            saveNotification(rentalRecordCancel.getRecordId(), rentalRecordCancel.getCreateAccount(), NotificationType.CANCEL_FAIL, rentalRecord.getId());
        });
    }

    @Override
    @Transactional
    public void unexpectedStatusChange(int id, String description, RentalRecordStatus newStatus) {
        Optional<RentalRecord> optionalRentalRecord = recordDAO.findById(id);
        RentalRecord rentalRecord = optionalRentalRecord.orElseThrow(() -> new NotFoundException("沒有此租借紀錄：" + id));
        if (rentalRecord.getStatus().isCanChangeTo(newStatus)) {
            saveStatusChangeLog(rentalRecord, newStatus, description);
            rentalRecord.setStatus(newStatus);
            recordDAO.update(rentalRecord);
            if (newStatus == RentalRecordStatus.BE_CLAIM) {
                User renter = rentalRecord.getUserByRenterAccount();
                renter.setLocked(true);
                userDAO.update(renter);
            }
        } else {
            throw new ChangeRentalRecordStatusFailException(rentalRecord.getStatus(), newStatus);
        }
    }

    @Override
    public String getChangeLogDescription(int id, RentalRecordStatus status) {
        RentalRecordStatusChangeLog log = statusChangeLogDAO.findById(new RentalRecordStatusChangeLogId(id, status))
                .orElseThrow(() -> new NotFoundException("無此異動紀錄"));
        RentalRecord record = log.getRecord();
        String loginUserAccount = SecurityUtils.getLoginUserAccount();
        User loginUser = userDAO.findById(loginUserAccount).orElseThrow();
        ProductGroup productGroup = record.getProductGroupByProductGroupId();
        if (loginUser.isManager() ||
                StringUtils.isEquals(record.getRenterAccount(), loginUserAccount) ||
                StringUtils.isEquals(productGroup.getCreateAccount(), loginUserAccount)) {
            return log.getDescription();
        } else {
            throw new NotRentalRecordOwnerException(id, loginUserAccount);
        }
    }

    @Override
    public List<RentalRecordIndexBean> searchIndexBean(@NonNull RentalRecordIndexFilterBean filterBean) {
        return recordDAO.findAll()
                .stream()
                .filter(rentalRecord -> filterBean.getStatus() == null || rentalRecord.getStatus() == filterBean.getStatus())
                .filter(rentalRecord -> filterBean.getRentalStartDate() == null || filterBean.isAfterOrEqualsStartDate(rentalRecord.getRentalDate().toLocalDate()))
                .filter(rentalRecord -> filterBean.getRentalEndDate() == null || filterBean.isBeforeOrEqualsStartDate(rentalRecord.getRentalDate().toLocalDate()))
                .filter(rentalRecord -> (filterBean.getRentalStartDate() == null && filterBean.getRentalEndDate() == null) || filterBean.isBetweenStartDateAndEndDate(rentalRecord.getRentalDate().toLocalDate()))
                .map(indexTransformer::transferToBean)
                .peek(indexBean -> indexBean.setLastChangeStatusDescription(
                        statusChangeLogDAO.findByRecordIdAndToStatus(indexBean.getId(), indexBean.getStatus())
                                .map(RentalRecordStatusChangeLog::getDescription)
                                .orElse("無"))
                )
                .collect(Collectors.toList());
    }

    @Override
    public void createComment(int id, byte comment) {
        if (MathUtils.isInRange(comment, 1, 5)) {
            RentalRecord rentalRecord = recordDAO.findById(id).orElseThrow(() -> new NotFoundException("找不到此紀錄：" + id));
            if (rentalRecord.getStatus().isCanChangeTo(RentalRecordStatus.COMMENTED)) {
                saveStatusChangeLog(rentalRecord, RentalRecordStatus.COMMENTED, "評價完成");
                rentalRecord.setStatus(RentalRecordStatus.COMMENTED);
                recordDAO.update(rentalRecord);

                UserComment productGroupComment = new UserComment();
                productGroupComment.setUserAccount(rentalRecord.getProductGroupByProductGroupId().getCreateAccount());
                productGroupComment.setComment(comment);
                userCommentDAO.save(productGroupComment);
            } else {
                throw new ChangeRentalRecordStatusFailException(rentalRecord.getStatus(), RentalRecordStatus.COMMENTED);
            }
        } else {
            throw new InvalidCommentRangeException(comment);
        }
    }
}
