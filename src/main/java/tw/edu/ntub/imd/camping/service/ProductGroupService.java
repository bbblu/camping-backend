package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.ProductGroupBean;
import tw.edu.ntub.imd.camping.bean.ProductTypeBean;

import java.util.List;

public interface ProductGroupService extends BaseService<ProductGroupBean, Integer> {
    List<ProductTypeBean> searchAllProductType();

    void deleteProduct(Integer productId);

    void deleteProductImage(Integer productImageId);

    void deleteProductRelatedLink(Integer productRelatedLinkId);
}
