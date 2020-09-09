package tw.edu.ntub.imd.camping.util;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.birc.common.util.BooleanUtils;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.*;
import tw.edu.ntub.imd.camping.exception.CouldNotBorrowException;
import tw.edu.ntub.imd.camping.exception.NotFoundException;

import java.util.List;

@UtilityClass
public class OwnerChecker {

    public void checkCanBorrowProductGroup(CanBorrowProductGroupDAO canBorrowProductGroupDAO, Integer productGroupId) {
        if (BooleanUtils.isFalse(canBorrowProductGroupDAO.existsById(productGroupId))) {
            throw new CouldNotBorrowException();
        }
    }

    public void checkIsProductGroupOwner(ProductGroupDAO productGroupDAO, Integer id) {
        if (BooleanUtils.isFalse(productGroupDAO.existsByIdAndCreateAccount(id, SecurityUtils.getLoginUserAccount()))) {
            throw new NotFoundException("找不到對應的商品群組");
        }
    }

    public void checkIsAllProductOwner(ProductDAO productDAO, List<Integer> productIdList) {
        if (BooleanUtils.isFalse(
                productDAO.existsByIdInAndProductGroupByGroupId_CreateAccount(productIdList, SecurityUtils.getLoginUserAccount())
        )) {
            throw new NotFoundException("找不到部份商品");
        }
    }

    public void checkIsProductImageOwner(ProductImageDAO productImageDAO, Integer productImageId) {
        if (BooleanUtils.isFalse(
                productImageDAO.existsByIdAndProductByProductId_ProductGroupByGroupId_CreateAccount(
                        productImageId,
                        SecurityUtils.getLoginUserAccount()
                )
        )) {
            throw new NotFoundException("找不到對應的商品圖");
        }
    }

    public void checkIsProductRelatedLinkOwner(ProductRelatedLinkDAO productRelatedLinkDAO, Integer productRelatedLinkId) {
        if (BooleanUtils.isFalse(
                productRelatedLinkDAO.existsByIdAndProductByProductId_ProductGroupByGroupId_CreateAccount(
                        productRelatedLinkId,
                        SecurityUtils.getLoginUserAccount()
                )
        )) {
            throw new NotFoundException("找不到對應的商品相關連結");
        }
    }
}
