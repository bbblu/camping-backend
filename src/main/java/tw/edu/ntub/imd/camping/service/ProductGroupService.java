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

    List<ProductBrandBean> searchAllBrand();

    List<ProductTypeBean> searchTypeByBrand(int brand);

    List<ProductSubTypeBean> searchSubTypeByBrandAndType(int brand, int type);

    long getRecommendPrice(int brand, int type, int subType);
}
