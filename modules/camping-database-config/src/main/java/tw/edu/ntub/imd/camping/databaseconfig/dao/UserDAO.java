package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;

@Repository
public interface UserDAO extends BaseDAO<User, String> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.account = :account AND u.locked = true")
    boolean isLocked(@Param("account") String account);
}
