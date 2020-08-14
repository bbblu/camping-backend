package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.CanBorrowProductGroupBean;
import tw.edu.ntub.imd.camping.bean.ProductGroupBean;
import tw.edu.ntub.imd.camping.bean.ProductGroupFilterDataBean;
import tw.edu.ntub.imd.camping.bean.ProductTypeBean;

import java.util.List;

public interface ProductGroupService extends BaseService<ProductGroupBean, Integer> {
    List<ProductTypeBean> searchAllProductType();

    List<CanBorrowProductGroupBean> searchCanBorrowProductGroup(ProductGroupFilterDataBean filterData);

    void deleteProduct(Integer productId);

    void deleteProductImage(Integer productImageId);

    void deleteProductRelatedLink(Integer productRelatedLinkId);
}
