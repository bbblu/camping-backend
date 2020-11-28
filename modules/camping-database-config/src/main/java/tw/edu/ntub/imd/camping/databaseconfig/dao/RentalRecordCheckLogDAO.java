package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordCheckLog;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import java.util.List;

@Repository
public interface RentalRecordCheckLogDAO extends BaseDAO<RentalRecordCheckLog, Integer> {

    List<RentalRecordCheckLog> findByRecordIdAndRecordStatus(int recordId, RentalRecordStatus recordStatus);
}
