package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.RentalRecordBean;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.*;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord_;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.exception.CanceledRentalRecordException;
import tw.edu.ntub.imd.camping.exception.LastRentalRecordStatusException;
import tw.edu.ntub.imd.camping.exception.NotFoundException;
import tw.edu.ntub.imd.camping.exception.NotRentalRecordOwnerException;
import tw.edu.ntub.imd.camping.service.RentalRecordService;
import tw.edu.ntub.imd.camping.service.transformer.RentalDetailTransformer;
import tw.edu.ntub.imd.camping.service.transformer.RentalRecordTransformer;
import tw.edu.ntub.imd.camping.util.OwnerChecker;
import tw.edu.ntub.imd.camping.util.TransactionUtils;

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

    public RentalRecordServiceImpl(
            RentalRecordDAO recordDAO,
            RentalRecordTransformer transformer,
            RentalDetailDAO detailDAO,
            RentalDetailTransformer detailTransformer,
            ProductGroupDAO productGroupDAO,
            ProductDAO productDAO,
            CanBorrowProductGroupDAO canBorrowProductGroupDAO,
            TransactionUtils transactionUtils) {
        super(recordDAO, transformer);
        this.recordDAO = recordDAO;
        this.transformer = transformer;
        this.detailDAO = detailDAO;
        this.detailTransformer = detailTransformer;
        this.productGroupDAO = productGroupDAO;
        this.productDAO = productDAO;
        this.canBorrowProductGroupDAO = canBorrowProductGroupDAO;
        this.transactionUtils = transactionUtils;
    }

    @Override
    public RentalRecordBean save(RentalRecordBean rentalRecordBean) {
        OwnerChecker.checkCanBorrowProductGroup(canBorrowProductGroupDAO, rentalRecordBean.getProductGroupId());

        RentalRecord rentalRecord = transformer.transferToEntity(rentalRecordBean);
        ProductGroup productGroup = productGroupDAO.findById(rentalRecordBean.getProductGroupId()).orElseThrow();
        int transactionId = transactionUtils.createTransaction(rentalRecordBean.getRenterCreditCard(), productGroup.getBankAccount(), productGroup.getPrice());
        rentalRecord.setTransactionId(transactionId);
        String creditCardId = rentalRecord.getRenterCreditCardId();
        rentalRecord.setRenterCreditCardId("************".concat(StringUtils.mid(creditCardId, -4)));
        RentalRecord saveResult = recordDAO.saveAndFlush(rentalRecord);
        saveDetail(saveResult.getId(), saveResult.getProductGroupId());
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

    @Override
    public List<RentalRecordBean> searchByRenterAccount(String renterAccount) {
        return transformer.transferToBeanList(recordDAO.findByRenterAccountAndEnableIsTrue(renterAccount, Sort.by(Sort.Order.desc(RentalRecord_.RENTAL_DATE))));
    }

    @Override
    public List<RentalRecordBean> searchByProductGroupCreateAccount(String productGroupCreateAccount) {
        return transformer.transferToBeanList(recordDAO.findAllBorrowRecord(productGroupCreateAccount, Sort.by(Sort.Order.desc(RentalRecord_.RENTAL_DATE))));
    }

    @Override
    public void updateStatusToNext(int id) {
        RentalRecord rentalRecord = getUpdateRentalRecord(id);
        RentalRecordStatus status = rentalRecord.getStatus();
        if (status == RentalRecordStatus.CHECKED) {
            throw new LastRentalRecordStatusException();
        } else {
            rentalRecord.setStatus(status.next());
            recordDAO.save(rentalRecord);
        }
    }

    private RentalRecord getUpdateRentalRecord(int id) {
        Optional<RentalRecord> optionalRentalRecord = recordDAO.findById(id);
        RentalRecord rentalRecord = optionalRentalRecord.orElseThrow(() -> new NotFoundException("無此紀錄：" + id));
        if (recordDAO.isNotRenterAndProductGroupCreator(id, SecurityUtils.getLoginUserAccount())) {
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
    public void cancelRecord(int id, String cancelDetail) {
        RentalRecord rentalRecord = getUpdateRentalRecord(id);
    }
}
