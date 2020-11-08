package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordStatusChangeLog;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordStatusChangeLogId;

@Repository
public interface RentalRecordStatusChangeLogDAO extends BaseDAO<RentalRecordStatusChangeLog, RentalRecordStatusChangeLogId> {

}
