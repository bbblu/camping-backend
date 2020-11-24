package tw.edu.ntub.imd.camping.databaseconfig.dao;

import tw.edu.ntub.imd.camping.databaseconfig.entity.UserBadRecord;

import java.util.List;

public interface UserBadRecordDAO extends BaseDAO<UserBadRecord, Integer> {
    List<UserBadRecord> findByUserAccount(String account);
}
