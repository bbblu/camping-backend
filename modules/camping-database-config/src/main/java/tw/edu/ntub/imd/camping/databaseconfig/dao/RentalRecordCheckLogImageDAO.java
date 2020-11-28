package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordCheckLogImage;

import java.util.List;

@Repository
public interface RentalRecordCheckLogImageDAO extends BaseDAO<RentalRecordCheckLogImage, Integer> {

    List<RentalRecordCheckLogImage> findByLogId(Integer logId);
}
