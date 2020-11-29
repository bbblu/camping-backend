package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordCheckLog;

import java.util.List;

@Repository
public interface RentalRecordCheckLogDAO extends BaseDAO<RentalRecordCheckLog, Integer> {

    List<RentalRecordCheckLog> findByRecordIdOrderByIdDesc(int recordId);
}
