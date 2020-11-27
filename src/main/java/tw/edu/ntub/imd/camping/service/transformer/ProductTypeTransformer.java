package tw.edu.ntub.imd.camping.service.transformer;

import tw.edu.ntub.imd.camping.bean.ProductTypeBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductType;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.BrandProductType;

public interface ProductTypeTransformer extends BeanEntityTransformer<ProductTypeBean, ProductType> {

    ProductTypeBean transferBrandProductTypeToBean(BrandProductType brandProductType);
}
