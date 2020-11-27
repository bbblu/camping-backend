package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductImage;

import javax.persistence.PrePersist;

public class ProductImageListener {

    @PrePersist
    private void preSave(ProductImage productImage) {
        if (productImage.isEnable() == null) {
            productImage.setEnable(true);
        }
    }
}
