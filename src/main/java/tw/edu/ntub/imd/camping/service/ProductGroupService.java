package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.*;

import java.util.List;

public interface ProductGroupService extends BaseService<ProductGroupBean, Integer> {
    List<ProductTypeBean> searchAllProductType();

    List<CanBorrowProductGroupBean> searchCanBorrowProductGroup(ProductGroupFilterDataBean filterData);

    List<CanBorrowProductGroupBean> searchCanBorrowProductGroupByCreateAccount(String createAccount);

    void updateProduct(List<ProductBean> productBeanList);

    void deleteProduct(Integer productId);

    void deleteProductImage(Integer productImageId);

    void createComment(int id, byte comment);

    List<ProductSubTypeBean> searchAllSubType();

    long getRecommendPrice(int type, int subType);
}
