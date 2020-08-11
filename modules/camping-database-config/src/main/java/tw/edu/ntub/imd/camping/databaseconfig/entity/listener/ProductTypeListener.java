package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductType;

import javax.persistence.PrePersist;

public class ProductTypeListener {

    @PrePersist
    private void preSave(ProductType productType) {
        if (productType.isEnable() == null) {
            productType.setEnable(true);
        }
    }
}
