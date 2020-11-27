package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductSubType;

import javax.persistence.PrePersist;

public class ProductSubTypeListener {

    @PrePersist
    public void preSave(ProductSubType productSubType) {
        if (productSubType.isEnable() == null) {
            productSubType.setEnable(true);
        }
    }
}
