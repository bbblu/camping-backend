package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductBrand;

import javax.persistence.PrePersist;

public class ProductBrandListener {

    @PrePersist
    public void preSave(ProductBrand productBrand) {
        if (productBrand.isEnable() == null) {
            productBrand.setEnable(true);
        }
    }
}
