package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Notification;

import java.util.List;

@Repository
public interface NotificationDAO extends BaseDAO<Notification, Integer> {
    long countByUserAccountAndReadDateIsNull(String userAccount);

    List<Notification> findByUserAccount(String userAccount);
}
