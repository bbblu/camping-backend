package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.ProductGroupBean;

public interface ProductGroupService extends BaseService<ProductGroupBean, Integer> {
    void deleteProduct(Integer productId);
}
