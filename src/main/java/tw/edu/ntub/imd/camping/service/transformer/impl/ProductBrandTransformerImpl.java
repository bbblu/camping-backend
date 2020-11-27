package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ProductBrandBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductBrand;
import tw.edu.ntub.imd.camping.service.transformer.ProductBrandTransformer;

@Component
public class ProductBrandTransformerImpl implements ProductBrandTransformer {
    @NonNull
    @Override
    public ProductBrand transferToEntity(@NonNull ProductBrandBean productBrandBean) {
        return JavaBeanUtils.copy(productBrandBean, new ProductBrand());
    }

    @NonNull
    @Override
    public ProductBrandBean transferToBean(@NonNull ProductBrand productBrand) {
        return JavaBeanUtils.copy(productBrand, new ProductBrandBean());
    }
}
