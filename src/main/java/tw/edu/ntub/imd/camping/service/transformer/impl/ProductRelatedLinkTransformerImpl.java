package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ProductRelatedLinkBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductRelatedLink;
import tw.edu.ntub.imd.camping.service.transformer.ProductRelatedLinkTransformer;

import javax.annotation.Nonnull;

@Component
public class ProductRelatedLinkTransformerImpl implements ProductRelatedLinkTransformer {
    @Nonnull
    @Override
    public ProductRelatedLink transferToEntity(@Nonnull ProductRelatedLinkBean productRelatedLinkBean) {
        return JavaBeanUtils.copy(productRelatedLinkBean, new ProductRelatedLink());
    }

    @Nonnull
    @Override
    public ProductRelatedLinkBean transferToBean(@Nonnull ProductRelatedLink productRelatedLink) {
        return JavaBeanUtils.copy(productRelatedLink, new ProductRelatedLinkBean());
    }
}
