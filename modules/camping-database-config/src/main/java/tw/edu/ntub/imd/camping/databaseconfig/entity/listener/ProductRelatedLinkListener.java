package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductRelatedLink;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class ProductRelatedLinkListener {

    @PrePersist
    private void preSave(ProductRelatedLink productRelatedLink) {
        if (productRelatedLink.isEnable() == null) {
            productRelatedLink.setEnable(true);
        }
        if (productRelatedLink.getCreateDate() == null) {
            productRelatedLink.setCreateDate(LocalDateTime.now());
        }
        if (productRelatedLink.getLastModifyDate() == null) {
            productRelatedLink.setLastModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    private void preUpdate(ProductRelatedLink productRelatedLink) {
        productRelatedLink.setLastModifyDate(LocalDateTime.now());
    }
}
