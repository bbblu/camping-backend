package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;

import java.util.List;

@Repository
public interface RentalRecordDAO extends BaseDAO<RentalRecord, Integer> {
    List<RentalRecord> findByRenterAccountAndEnableIsTrue(String renterAccount, Sort sort);

    @Query("FROM RentalRecord r " +
            "INNER JOIN ProductGroup pg ON r.productGroupId = pg.id " +
            "WHERE pg.createAccount = :createAccount AND r.enable = true")
    List<RentalRecord> findAllBorrowRecord(@Param("createAccount") String productGroupCreateAccount, Sort sort);
}
