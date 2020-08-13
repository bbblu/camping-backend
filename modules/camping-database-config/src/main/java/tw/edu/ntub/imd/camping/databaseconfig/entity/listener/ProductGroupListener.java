package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class ProductGroupListener {

    @PrePersist
    private void preSave(ProductGroup productGroup) {
        if (productGroup.isEnable() == null) {
            productGroup.setEnable(true);
        }
        if (productGroup.getCreateAccount() == null) {
            productGroup.setCreateAccount(SecurityUtils.getLoginUserAccount());
        }
        if (productGroup.getCreateDate() == null) {
            productGroup.setCreateDate(LocalDateTime.now());
        }
        if (productGroup.getLastModifyAccount() == null) {
            productGroup.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        }
        if (productGroup.getLastModifyDate() == null) {
            productGroup.setLastModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    private void preUpdate(ProductGroup productGroup) {
        productGroup.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        productGroup.setLastModifyDate(LocalDateTime.now());
    }
}
