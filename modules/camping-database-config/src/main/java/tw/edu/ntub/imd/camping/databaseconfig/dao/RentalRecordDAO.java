package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;

import java.util.List;

@Repository
public interface RentalRecordDAO extends BaseDAO<RentalRecord, Integer> {
    List<RentalRecord> findByRenterAccountAndEnableIsTrue(String renterAccount, Sort sort);
}
