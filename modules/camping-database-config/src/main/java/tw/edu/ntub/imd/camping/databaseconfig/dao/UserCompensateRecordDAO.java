package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.UserCompensateRecord;

import java.util.Optional;

@Repository
public interface UserCompensateRecordDAO extends BaseDAO<UserCompensateRecord, Integer> {
    Optional<UserCompensateRecord> findByUserAccountAndCompensatedIsFalse(String userAccount);

    boolean existsByUserAccountAndCompensatedIsFalse(String userAccount);
}
