package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Product;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class ProductListener {

    @PrePersist
    private void preSave(Product product) {
        if (product.isEnable() == null) {
            product.setEnable(true);
        }
        if (product.getLastModifyAccount() == null) {
            product.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        }
        if (product.getLastModifyDate() == null) {
            product.setLastModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    private void preUpdate(Product product) {
        product.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        product.setLastModifyDate(LocalDateTime.now());
    }
}
