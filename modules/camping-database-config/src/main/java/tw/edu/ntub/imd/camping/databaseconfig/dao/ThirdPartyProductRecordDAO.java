package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ThirdPartyProductRecord;

import java.util.List;

@Repository
public interface ThirdPartyProductRecordDAO extends BaseDAO<ThirdPartyProductRecord, Integer> {

    List<ThirdPartyProductRecord> findByEnableIsTrue();
}
