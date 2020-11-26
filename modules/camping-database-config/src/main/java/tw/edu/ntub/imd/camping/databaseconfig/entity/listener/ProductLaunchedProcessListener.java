package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductLaunchedProcess;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProductLaunchedProcessStatus;

import javax.persistence.PrePersist;

public class ProductLaunchedProcessListener {

    @PrePersist
    private void preSave(ProductLaunchedProcess productLaunchedProcess) {
        if (productLaunchedProcess.getStatus() == null) {
            productLaunchedProcess.setStatus(ProductLaunchedProcessStatus.UNPROCESSED);
        }
    }
}
