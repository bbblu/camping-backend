package tw.edu.ntub.imd.camping.service.transformer.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ProductGroupBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductGroupCommentDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.service.transformer.ProductGroupTransformer;
import tw.edu.ntub.imd.camping.service.transformer.ProductTransformer;
import tw.edu.ntub.imd.camping.service.transformer.UserTransformer;

import javax.annotation.Nonnull;

@AllArgsConstructor
@Component
public class ProductGroupTransformerImpl implements ProductGroupTransformer {
    private final ProductDAO productDAO;
    private final ProductTransformer productTransformer;
    private final UserTransformer userTransformer;
    private final ProductGroupCommentDAO commentDAO;

    @Nonnull
    @Override
    public ProductGroup transferToEntity(@Nonnull ProductGroupBean productGroupBean) {
        return JavaBeanUtils.copy(productGroupBean, new ProductGroup());
    }

    @Nonnull
    @Override
    public ProductGroupBean transferToBean(@Nonnull ProductGroup productGroup) {
        ProductGroupBean result = JavaBeanUtils.copy(productGroup, new ProductGroupBean());
        if (productGroup.getUserByCreateAccount() != null) {
            result.setCreateUser(userTransformer.transferToBean(productGroup.getUserByCreateAccount()));
        }
        if (productGroup.getId() != null && CollectionUtils.isEmpty(result.getProductArray())) {
            result.setProductArray(productTransformer.transferToBeanList(
                    productDAO.findByGroupId(productGroup.getId())
            ));
            result.setComment((Double) commentDAO.getAverageCommentByGroupId(productGroup.getId()));
        }
        return result;
    }
}
