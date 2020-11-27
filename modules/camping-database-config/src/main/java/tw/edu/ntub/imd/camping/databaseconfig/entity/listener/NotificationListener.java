package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.Notification;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.NotificationType;

import javax.persistence.PrePersist;

public class NotificationListener {
    @PrePersist
    public void preSave(Notification notification) {
        if (notification.getType() == null) {
            notification.setType(NotificationType.RENTAL);
        }
    }
}
