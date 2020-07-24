package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;

@Repository
public interface UserDAO extends BaseDAO<User, String> {

}
