package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.*;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.*;
import tw.edu.ntub.imd.camping.databaseconfig.entity.*;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.NotificationType;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.dto.file.uploader.MultipartFileUploader;
import tw.edu.ntub.imd.camping.dto.file.uploader.UploadResult;
import tw.edu.ntub.imd.camping.exception.*;
import tw.edu.ntub.imd.camping.factory.RentalRecordStatusMapperFactory;
import tw.edu.ntub.imd.camping.mapper.RentalRecordStatusMapper;
import tw.edu.ntub.imd.camping.service.RentalRecordService;
import tw.edu.ntub.imd.camping.service.transformer.RentalDetailTransformer;
import tw.edu.ntub.imd.camping.service.transformer.RentalRecordCheckLogTransformer;
import tw.edu.ntub.imd.camping.service.transformer.RentalRecordIndexTransformer;
import tw.edu.ntub.imd.camping.service.transformer.RentalRecordTransformer;
import tw.edu.ntub.imd.camping.util.NotificationUtils;
import tw.edu.ntub.imd.camping.util.OwnerChecker;

import java.time.Duration;
import java.util.Arrays;
import java.util.EnumSet;
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
    private final RentalRecordStatusChangeLogDAO statusChangeLogDAO;
    private final UserDAO userDAO;
    private final RentalRecordIndexTransformer indexTransformer;
    private final RentalRecordStatusMapperFactory statusMapperFactory;
    private final NotificationDAO notificationDAO;
    private final NotificationUtils notificationUtils;
    private final UserCommentDAO userCommentDAO;
    private final UserCompensateRecordDAO userCompensateRecordDAO;
    private final RentalRecordCheckLogDAO checkLogDAO;
    private final RentalRecordCheckLogTransformer checkLogTransformer;
    private final RentalRecordCheckLogImageDAO checkLogImageDAO;
    private final MultipartFileUploader fileUploader;

    public RentalRecordServiceImpl(
            RentalRecordDAO recordDAO,
            RentalRecordTransformer transformer,
            RentalDetailDAO detailDAO,
            RentalDetailTransformer detailTransformer,
            ProductGroupDAO productGroupDAO,
            ProductDAO productDAO,
            CanBorrowProductGroupDAO canBorrowProductGroupDAO,
            RentalRecordStatusChangeLogDAO statusChangeLogDAO,
            UserDAO userDAO,
            RentalRecordIndexTransformer indexTransformer,
            RentalRecordStatusMapperFactory statusMapperFactory,
            NotificationDAO notificationDAO,
            NotificationUtils notificationUtils,
            UserCommentDAO userCommentDAO,
            UserCompensateRecordDAO userCompensateRecordDAO,
            RentalRecordCheckLogDAO checkLogDAO,
            RentalRecordCheckLogTransformer checkLogTransformer,
            RentalRecordCheckLogImageDAO checkLogImageDAO,
            MultipartFileUploader fileUploader) {
        super(recordDAO, transformer);
        this.recordDAO = recordDAO;
        this.transformer = transformer;
        this.detailDAO = detailDAO;
        this.detailTransformer = detailTransformer;
        this.productGroupDAO = productGroupDAO;
        this.productDAO = productDAO;
        this.canBorrowProductGroupDAO = canBorrowProductGroupDAO;
        this.statusChangeLogDAO = statusChangeLogDAO;
        this.userDAO = userDAO;
        this.indexTransformer = indexTransformer;
        this.statusMapperFactory = statusMapperFactory;
        this.notificationDAO = notificationDAO;
        this.notificationUtils = notificationUtils;
        this.userCommentDAO = userCommentDAO;
        this.userCompensateRecordDAO = userCompensateRecordDAO;
        this.checkLogDAO = checkLogDAO;
        this.checkLogTransformer = checkLogTransformer;
        this.checkLogImageDAO = checkLogImageDAO;
        this.fileUploader = fileUploader;
    }

    @Override
    public RentalRecordBean save(RentalRecordBean rentalRecordBean) {
        OwnerChecker.checkCanBorrowProductGroup(canBorrowProductGroupDAO, rentalRecordBean.getProductGroupId());
        if (userDAO.isLocked(SecurityUtils.getLoginUserAccount())) {
            throw new LockedUserException();
        } else if (
                userCompensateRecordDAO.existsByUserAccountAndCompensatedIsFalse(SecurityUtils.getLoginUserAccount())
        ) {
            throw new NotCompensateRentalRecordException();
        }

        ProductGroup productGroup = productGroupDAO.findById(rentalRecordBean.getProductGroupId()).orElseThrow();
        if (StringUtils.isEquals(productGroup.getCreateAccount(), SecurityUtils.getLoginUserAccount())) {
            throw new RentalSelfProductException();
        }

        RentalRecord rentalRecord = transformer.transferToEntity(rentalRecordBean);
        Duration betweenStartDateAndEndDate = Duration.between(rentalRecordBean.getBorrowStartDate(), rentalRecordBean.getBorrowEndDate());
        final int EXCLUSIVE_END_DATE = 1;
        int borrowDays = (int) betweenStartDateAndEndDate.toDays() + EXCLUSIVE_END_DATE;
        rentalRecord.setPrice(productGroup.getPrice() * borrowDays);
        RentalRecord saveResult = recordDAO.saveAndFlush(rentalRecord);
        saveDetail(saveResult.getId(), saveResult.getProductGroupId());
        saveResult.setProductGroupByProductGroupId(productGroup);
        notificationUtils.create(saveResult);
        return transformer.transferToBean(saveResult);
    }

    private void saveDetail(int recordId, int productGroupId) {
        detailDAO.saveAll(productDAO.findByGroupIdAndEnableIsTrue(productGroupId)
                .parallelStream()
                .map(detailTransformer::transferProductToEntity)
                .peek(rentalDetail -> rentalDetail.setRecordId(recordId))
                .collect(Collectors.toList())
        );
    }

    @Override
    public List<RentalRecordBean> searchByRenterAccount(String renterAccount) {
        return transformer.transferToBeanList(recordDAO.findByRenterAccountAndEnableIsTrue(renterAccount, Sort.by(Sort.Order.desc(RentalRecord_.ID))));
    }

    @Override
    public List<RentalRecordBean> searchByProductGroupCreateAccount(String productGroupCreateAccount) {
        return transformer.transferToBeanList(recordDAO.findAllBorrowRecord(productGroupCreateAccount, Sort.by(Sort.Order.desc(RentalRecord_.ID))));
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
        return recordDAO.findAll(Sort.by(Sort.Order.desc(RentalRecord_.ID)))
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
    public void updateStatus(RentalRecordStatusChangeBean statusChangeBean) {
        RentalRecord record = recordDAO.findById(statusChangeBean.getId())
                .orElseThrow(() -> new NotFoundException("無此租借紀錄"));

        RentalRecordStatus originStatus = record.getStatus();
        RentalRecordStatusMapper mapper = statusMapperFactory.create(originStatus);
        EnumSet<RentalRecordStatus> canChangeToStatusSet = mapper.getCanChangeToStatusSet();
        if (canChangeToStatusSet.contains(statusChangeBean.getNewStatus())) {
            mapper.validate(record, statusChangeBean.getNewStatus());

            mapper.beforeChange(record, statusChangeBean.getNewStatus(), statusChangeBean.getPayload());
            record.setStatus(statusChangeBean.getNewStatus());
            recordDAO.updateAndFlush(record);
            saveChangeLog(
                    statusChangeBean.getId(),
                    originStatus,
                    statusChangeBean.getNewStatus(),
                    statusChangeBean.getChangeDescription()
            );
            notificationUtils.create(record);
            mapper.afterChange(record, originStatus, statusChangeBean.getPayload());
        } else {
            throw new RentalRecordStatusChangeException(record.getStatus(), statusChangeBean.getNewStatus());
        }
    }

    private void saveChangeLog(int id, RentalRecordStatus from, RentalRecordStatus to, String description) {
        RentalRecordStatusChangeLog changeLog = new RentalRecordStatusChangeLog();
        changeLog.setRecordId(id);
        changeLog.setFromStatus(from);
        changeLog.setToStatus(to);
        changeLog.setDescription(description);
        statusChangeLogDAO.save(changeLog);
    }

    @Override
    public void saveComment(int id, int comment) {
        RentalRecord record = recordDAO.findById(id).orElseThrow(() -> new NotFoundException("無此租借紀錄"));
        if (record.getStatus() != RentalRecordStatus.NOT_COMMENT) {
            throw new RentalRecordStatusChangeException(record.getStatus(), RentalRecordStatus.ALREADY_COMMENT);
        }

        UserComment userComment = new UserComment();
        userComment.setRentalRecordId(id);
        NotificationType notificationType;
        if (StringUtils.isEquals(record.getRenterAccount(), SecurityUtils.getLoginUserAccount())) {
            ProductGroup productGroup = record.getProductGroupByProductGroupId();
            userComment.setUserAccount(productGroup.getCreateAccount());
            notificationType = NotificationType.PRODUCT_OWNER_ALREADY_COMMENT;
        } else {
            userComment.setUserAccount(record.getRenterAccount());
            notificationType = NotificationType.RENTER_ALREADY_COMMENT;
        }
        if (userCommentDAO.exists(Example.of(userComment))) {
            throw new DuplicateCommentException();
        } else {
            userComment.setComment((byte) comment);
            userCommentDAO.saveAndFlush(userComment);
            if (userCommentDAO.existsByRentalRecordIdAndUserAccountAndCommentAccount(
                    id,
                    userComment.getCommentAccount(),
                    userComment.getUserAccount()
            )) {
                updateStatus(
                        RentalRecordStatusChangeBean.builder()
                                .id(id)
                                .newStatus(RentalRecordStatus.ALREADY_COMMENT)
                                .changeDescription("對方已評價")
                                .build()
                );
            } else {
                Notification notification = new Notification();
                notification.setRentalRecordId(record.getId());
                notification.setType(notificationType);
                notification.setUserAccount(userComment.getUserAccount());
                notification.setContent(notificationType.getMessage(record.getId()));
                notificationDAO.save(notification);
            }
        }
    }

    @Override
    public List<RentalRecordBean> searchByStatus(RentalRecordStatus status) {
        return CollectionUtils.map(recordDAO.findByStatus(status), transformer::transferToBean);
    }

    @Override
    public void saveProductStatus(int id, RentalRecordCheckLogBean productStatusBean) {
        RentalRecord record = recordDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("找不到此紀錄"));
        RentalRecordCheckLog checkLog = new RentalRecordCheckLog();
        checkLog.setRecordId(id);
        checkLog.setRecordStatus(record.getStatus());
        checkLog.setContent(productStatusBean.getContent());
        RentalRecordCheckLog saveResult = checkLogDAO.saveAndFlush(checkLog);
        Optional.ofNullable(productStatusBean.getImages())
                .stream()
                .flatMap(Arrays::stream)
                .map(imageFile -> fileUploader.upload(
                        imageFile,
                        "rental-record", String.valueOf(saveResult.getId())
                ))
                .map(UploadResult::getUrl)
                .map(RentalRecordCheckLogImage::new)
                .peek(image -> image.setLogId(saveResult.getId()))
                .forEach(checkLogImageDAO::save);
    }

    @Override
    public List<RentalRecordCheckLogBean> searchCheckLog(int id) {
        RentalRecord record = recordDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("找不到此紀錄"));
        return CollectionUtils.map(
                checkLogDAO.findByRecordIdAndRecordStatus(id, record.getStatus()),
                checkLogTransformer::transferToBean
        );
    }
}
