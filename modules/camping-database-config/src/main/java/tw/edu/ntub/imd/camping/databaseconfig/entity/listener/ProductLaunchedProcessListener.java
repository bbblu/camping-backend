package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductLaunchedProcess;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProductLaunchedProcessStatus;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class ProductLaunchedProcessListener {

    @PrePersist
    private void preSave(ProductLaunchedProcess productLaunchedProcess) {
        if (productLaunchedProcess.getStatus() == null) {
            productLaunchedProcess.setStatus(ProductLaunchedProcessStatus.UNPROCESSED);
        }
        if (productLaunchedProcess.getCreateDate() == null) {
            productLaunchedProcess.setCreateDate(LocalDateTime.now());
        }
        if (productLaunchedProcess.getLastModifyDate() == null) {
            productLaunchedProcess.setLastModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    private void preUpdate(ProductLaunchedProcess productLaunchedProcess) {
        productLaunchedProcess.setLastModifyDate(LocalDateTime.now());
    }
}
