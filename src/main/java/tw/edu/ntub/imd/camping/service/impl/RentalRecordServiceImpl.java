package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.camping.bean.RentalRecordBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.*;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.service.RentalRecordService;
import tw.edu.ntub.imd.camping.service.transformer.RentalDetailTransformer;
import tw.edu.ntub.imd.camping.service.transformer.RentalRecordTransformer;
import tw.edu.ntub.imd.camping.util.OwnerChecker;

import java.util.stream.Collectors;

@Service
public class RentalRecordServiceImpl extends BaseServiceImpl<RentalRecordBean, RentalRecord, Integer> implements RentalRecordService {
    private final RentalRecordDAO recordDAO;
    private final RentalRecordTransformer transformer;
    private final RentalDetailDAO detailDAO;
    private final RentalDetailTransformer detailTransformer;
    private final ProductDAO productDAO;
    private final CanBorrowProductGroupDAO canBorrowProductGroupDAO;
    private final ContactInformationDAO contactInformationDAO;

    public RentalRecordServiceImpl(
            RentalRecordDAO recordDAO,
            RentalRecordTransformer transformer,
            RentalDetailDAO detailDAO,
            RentalDetailTransformer detailTransformer,
            ProductDAO productDAO,
            CanBorrowProductGroupDAO canBorrowProductGroupDAO,
            ContactInformationDAO contactInformationDAO) {
        super(recordDAO, transformer);
        this.recordDAO = recordDAO;
        this.transformer = transformer;
        this.detailDAO = detailDAO;
        this.detailTransformer = detailTransformer;
        this.productDAO = productDAO;
        this.canBorrowProductGroupDAO = canBorrowProductGroupDAO;
        this.contactInformationDAO = contactInformationDAO;
    }

    @Override
    public RentalRecordBean save(RentalRecordBean rentalRecordBean) {
        OwnerChecker.checkCanBorrowProductGroup(canBorrowProductGroupDAO, rentalRecordBean.getProductGroupId());
        OwnerChecker.checkContactInformationOwner(contactInformationDAO, rentalRecordBean.getRenterContactInformationId());

        RentalRecord rentalRecord = transformer.transferToEntity(rentalRecordBean);
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
}
