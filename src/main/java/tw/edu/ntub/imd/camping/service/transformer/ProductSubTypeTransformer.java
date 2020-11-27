package tw.edu.ntub.imd.camping.service.transformer;

import tw.edu.ntub.imd.camping.bean.ProductSubTypeBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductSubType;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.BrandProductType;

public interface ProductSubTypeTransformer extends BeanEntityTransformer<ProductSubTypeBean, ProductSubType> {

    ProductSubTypeBean transferBrandProductTypeToBean(BrandProductType brandProductType);
}
