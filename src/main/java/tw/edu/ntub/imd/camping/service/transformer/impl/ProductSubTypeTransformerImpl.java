package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ProductSubTypeBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductSubType;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.BrandProductType;
import tw.edu.ntub.imd.camping.service.transformer.ProductSubTypeTransformer;

@Component
public class ProductSubTypeTransformerImpl implements ProductSubTypeTransformer {
    @NonNull
    @Override
    public ProductSubType transferToEntity(@NonNull ProductSubTypeBean productSubTypeBean) {
        return JavaBeanUtils.copy(productSubTypeBean, new ProductSubType());
    }

    @NonNull
    @Override
    public ProductSubTypeBean transferToBean(@NonNull ProductSubType productSubType) {
        return JavaBeanUtils.copy(productSubType, new ProductSubTypeBean());
    }

    @Override
    public ProductSubTypeBean transferBrandProductTypeToBean(BrandProductType brandProductType) {
        ProductSubTypeBean result = new ProductSubTypeBean();
        result.setId(brandProductType.getSubType());
        result.setName(brandProductType.getSubTypeName());
        return result;
    }
}
