package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordTerminateRecord;

import java.util.Optional;

@Repository
public interface RentalRecordTerminateRecordDAO extends BaseDAO<RentalRecordTerminateRecord, Integer> {

    Optional<RentalRecordTerminateRecord> findByRecordId(int recordId);
}
