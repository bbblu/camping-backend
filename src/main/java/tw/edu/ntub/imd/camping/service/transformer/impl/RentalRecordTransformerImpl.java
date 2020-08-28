package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.RentalRecordBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.RentalDetailDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.dto.CreditCard;
import tw.edu.ntub.imd.camping.service.transformer.ProductGroupTransformer;
import tw.edu.ntub.imd.camping.service.transformer.RentalDetailTransformer;
import tw.edu.ntub.imd.camping.service.transformer.RentalRecordTransformer;

import javax.annotation.Nonnull;

@Component
public class RentalRecordTransformerImpl implements RentalRecordTransformer {
    private final RentalDetailDAO detailDAO;
    private final RentalDetailTransformer detailTransformer;
    private final ProductGroupTransformer productGroupTransformer;

    public RentalRecordTransformerImpl(
            RentalDetailDAO detailDAO,
            RentalDetailTransformer detailTransformer,
            ProductGroupTransformer productGroupTransformer
    ) {
        this.detailDAO = detailDAO;
        this.detailTransformer = detailTransformer;
        this.productGroupTransformer = productGroupTransformer;
    }

    @Nonnull
    @Override
    public RentalRecord transferToEntity(@Nonnull RentalRecordBean rentalRecordBean) {
        RentalRecord result = JavaBeanUtils.copy(rentalRecordBean, new RentalRecord());
        if (rentalRecordBean.getRenterCreditCard() != null) {
            CreditCard renterCreditCard = rentalRecordBean.getRenterCreditCard();
            result.setRenterCreditCardId(renterCreditCard.getCardId());
        }
        return result;
    }

    @Nonnull
    @Override
    public RentalRecordBean transferToBean(@Nonnull RentalRecord rentalRecord) {
        RentalRecordBean result = JavaBeanUtils.copy(rentalRecord, new RentalRecordBean());
        if (rentalRecord.getProductGroupByProductGroupId() != null) {
            result.setProductGroup(productGroupTransformer.transferToBean(rentalRecord.getProductGroupByProductGroupId()));
        }
        result.setDetailBeanList(detailTransformer.transferToBeanList(
                detailDAO.findByRecordId(rentalRecord.getId())
        ));
        CreditCard creditCard = new CreditCard();
        creditCard.setCardId(rentalRecord.getRenterCreditCardId());
        result.setRenterCreditCard(creditCard);
        return result;
    }
}
