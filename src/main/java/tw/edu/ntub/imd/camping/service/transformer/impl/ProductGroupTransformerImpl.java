package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ProductGroupBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.service.transformer.ContactInformationTransformer;
import tw.edu.ntub.imd.camping.service.transformer.ProductGroupTransformer;
import tw.edu.ntub.imd.camping.service.transformer.ProductTransformer;

import javax.annotation.Nonnull;

@Component
public class ProductGroupTransformerImpl implements ProductGroupTransformer {
    private final ContactInformationTransformer contactInformationTransformer;
    private final ProductDAO productDAO;
    private final ProductTransformer productTransformer;

    public ProductGroupTransformerImpl(
            ContactInformationTransformer contactInformationTransformer,
            ProductDAO productDAO,
            ProductTransformer productTransformer
    ) {
        this.contactInformationTransformer = contactInformationTransformer;
        this.productDAO = productDAO;
        this.productTransformer = productTransformer;
    }

    @Nonnull
    @Override
    public ProductGroup transferToEntity(@Nonnull ProductGroupBean productGroupBean) {
        return JavaBeanUtils.copy(productGroupBean, new ProductGroup());
    }

    @Nonnull
    @Override
    public ProductGroupBean transferToBean(@Nonnull ProductGroup productGroup) {
        ProductGroupBean result = JavaBeanUtils.copy(productGroup, new ProductGroupBean());
        if (result.getContactInformation() == null) {
            result.setContactInformation(contactInformationTransformer.transferToBean(
                    productGroup.getContactInformationByContactInformationId()
            ));
        }
        if (productGroup.getId() != null && CollectionUtils.isEmpty(result.getProductArray())) {
            result.setProductArray(productTransformer.transferToBeanList(
                    productDAO.findByGroupId(productGroup.getId())
            ));
        }
        return result;
    }
}
