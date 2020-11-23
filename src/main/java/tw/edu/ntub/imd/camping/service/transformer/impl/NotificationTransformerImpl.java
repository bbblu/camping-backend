package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.NotificationBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Notification;
import tw.edu.ntub.imd.camping.service.transformer.NotificationTransformer;

@Component
public class NotificationTransformerImpl implements NotificationTransformer {

    @NonNull
    @Override
    public Notification transferToEntity(@NonNull NotificationBean notificationBean) {
        return JavaBeanUtils.copy(notificationBean, new Notification());
    }

    @NonNull
    @Override
    public NotificationBean transferToBean(@NonNull Notification notification) {
        return JavaBeanUtils.copy(notification, new NotificationBean());
    }
}
