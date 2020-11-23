package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import java.util.List;

@Repository
public interface RentalRecordDAO extends BaseDAO<RentalRecord, Integer> {
    List<RentalRecord> findByRenterAccountAndEnableIsTrue(String renterAccount, Sort sort);

    @Query("FROM RentalRecord r " +
            "INNER JOIN ProductGroup pg ON r.productGroupId = pg.id " +
            "WHERE pg.createAccount = :createAccount AND r.enable = true")
    List<RentalRecord> findAllBorrowRecord(@Param("createAccount") String productGroupCreateAccount, Sort sort);

    @Query("SELECT CASE WHEN(COUNT(r) = 0) THEN TRUE ELSE FALSE END " +
            "FROM RentalRecord r " +
            "INNER JOIN ProductGroup pg ON r.productGroupId = pg.id " +
            "WHERE r.id = :id AND (r.renterAccount = :account OR pg.createAccount = :account)")
    boolean isNotRenterAndProductGroupCreator(@Param("id") int id, @Param("account") String account);

    List<RentalRecord> findByStatus(RentalRecordStatus status);
}
