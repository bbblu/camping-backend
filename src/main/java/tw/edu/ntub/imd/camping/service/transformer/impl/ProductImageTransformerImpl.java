package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ProductImageBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductImage;
import tw.edu.ntub.imd.camping.service.transformer.ProductImageTransformer;

import javax.annotation.Nonnull;

@Component
public class ProductImageTransformerImpl implements ProductImageTransformer {
    @Nonnull
    @Override
    public ProductImage transferToEntity(@Nonnull ProductImageBean productImageBean) {
        return JavaBeanUtils.copy(productImageBean, new ProductImage());
    }

    @Nonnull
    @Override
    public ProductImageBean transferToBean(@Nonnull ProductImage productImage) {
        return JavaBeanUtils.copy(productImage, new ProductImageBean());
    }
}
