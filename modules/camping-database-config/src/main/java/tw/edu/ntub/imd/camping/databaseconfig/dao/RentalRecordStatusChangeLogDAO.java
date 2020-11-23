package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordStatusChangeLog;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordStatusChangeLogId;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import java.util.Optional;

@Repository
public interface RentalRecordStatusChangeLogDAO extends BaseDAO<RentalRecordStatusChangeLog, RentalRecordStatusChangeLogId> {

    Optional<RentalRecordStatusChangeLog> findByRecordIdAndToStatus(Integer recordId, RentalRecordStatus status);
}
