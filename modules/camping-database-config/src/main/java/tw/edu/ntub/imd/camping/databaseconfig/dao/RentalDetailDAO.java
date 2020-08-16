package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalDetail;

import java.util.List;

@Repository
public interface RentalDetailDAO extends BaseDAO<RentalDetail, Integer> {
    List<RentalDetail> findByRecordId(Integer recordId);
}
