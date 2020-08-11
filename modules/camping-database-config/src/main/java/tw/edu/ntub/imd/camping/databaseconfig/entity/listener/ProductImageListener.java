package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductImage;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class ProductImageListener {

    @PrePersist
    private void preSave(ProductImage productImage) {
        if (productImage.isEnable() == null) {
            productImage.setEnable(true);
        }
        if (productImage.getCreateDate() == null) {
            productImage.setCreateDate(LocalDateTime.now());
        }
        if (productImage.getLastModifyDate() == null) {
            productImage.setLastModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    private void preUpdate(ProductImage productImage) {
        productImage.setLastModifyDate(LocalDateTime.now());
    }
}
