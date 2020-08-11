package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroupComment;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class ProductGroupCommentListener {

    @PrePersist
    private void preSave(ProductGroupComment productGroupComment) {
        if (productGroupComment.getCommentDate() != null) {
            productGroupComment.setCommentDate(LocalDateTime.now());
        }
    }
}
