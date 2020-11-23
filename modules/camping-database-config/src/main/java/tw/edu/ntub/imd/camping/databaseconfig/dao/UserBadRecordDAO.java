package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.edu.ntub.imd.camping.databaseconfig.entity.UserBadRecord;

import java.util.List;

public interface UserBadRecordDAO extends BaseDAO<UserBadRecord, Integer> {
    @Query("SELECT r.type, COUNT(r) FROM UserBadRecord r WHERE r.userAccount = :account GROUP BY r.type")
    List<Object[]> findBadRecordCount(@Param("account") String account);
}
