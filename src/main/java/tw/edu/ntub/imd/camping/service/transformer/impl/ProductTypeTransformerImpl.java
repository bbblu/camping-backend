package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ProductTypeBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductType;
import tw.edu.ntub.imd.camping.service.transformer.ProductTypeTransformer;

import javax.annotation.Nonnull;

@Component
public class ProductTypeTransformerImpl implements ProductTypeTransformer {
    @Nonnull
    @Override
    public ProductType transferToEntity(@Nonnull ProductTypeBean productTypeBean) {
        return JavaBeanUtils.copy(productTypeBean, new ProductType());
    }

    @Nonnull
    @Override
    public ProductTypeBean transferToBean(@Nonnull ProductType productType) {
        return JavaBeanUtils.copy(productType, new ProductTypeBean());
    }
}
