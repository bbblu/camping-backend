package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordCancel;

import java.util.Optional;

@Repository
public interface RentalRecordCancelDAO extends BaseDAO<RentalRecordCancel, Integer> {
    @Query("SELECT c FROM RentalRecordCancel c WHERE c.recordId = :recordId AND (c.status = '0' OR c.status = '1')")
    Optional<RentalRecordCancel> findWaitResponseRecord(@Param("recordId") Integer recordId);
}
