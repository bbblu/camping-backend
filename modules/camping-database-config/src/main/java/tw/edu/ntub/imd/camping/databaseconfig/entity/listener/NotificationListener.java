package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.Notification;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class NotificationListener {
    @PrePersist
    public void preSave(Notification notification) {
        if (notification.getType() == null) {

        }
        if (notification.getSendDate() == null) {
            notification.setSendDate(LocalDateTime.now());
        }
    }
}
