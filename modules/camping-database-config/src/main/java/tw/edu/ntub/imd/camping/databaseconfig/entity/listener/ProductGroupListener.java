package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;

import javax.persistence.PrePersist;

public class ProductGroupListener {

    @PrePersist
    private void preSave(ProductGroup productGroup) {
        if (productGroup.isEnable() == null) {
            productGroup.setEnable(true);
        }
    }
}
