package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.Product;

import javax.persistence.PrePersist;

public class ProductListener {

    @PrePersist
    private void preSave(Product product) {
        if (product.isEnable() == null) {
            product.setEnable(true);
        }
    }
}
