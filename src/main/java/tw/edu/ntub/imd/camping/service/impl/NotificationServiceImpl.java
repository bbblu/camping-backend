package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.imd.camping.bean.NotificationBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.NotificationDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Notification;
import tw.edu.ntub.imd.camping.service.NotificationService;
import tw.edu.ntub.imd.camping.service.transformer.NotificationTransformer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl extends BaseViewServiceImpl<NotificationBean, Notification, Integer> implements NotificationService {
    private final NotificationDAO notificationDAO;
    private final NotificationTransformer transformer;

    public NotificationServiceImpl(NotificationDAO notificationDAO, NotificationTransformer transformer) {
        super(notificationDAO, transformer);
        this.notificationDAO = notificationDAO;
        this.transformer = transformer;
    }

    @Override
    public long getNotReadCount(String userAccount) {
        return notificationDAO.countByUserAccountAndReadDateIsNull(userAccount);
    }

    @Override
    public List<NotificationBean> searchByUserAccount(String userAccount) {
        List<Notification> notificationList = notificationDAO.findByUserAccount(userAccount);
        LocalDateTime readDate = LocalDateTime.now();
        notificationDAO.updateAll(notificationList.parallelStream()
                .filter(notification -> notification.getReadDate() == null)
                .peek(notification -> notification.setReadDate(readDate))
                .collect(Collectors.toList())
        );
        return CollectionUtils.map(notificationList, transformer::transferToBean);
    }
}
