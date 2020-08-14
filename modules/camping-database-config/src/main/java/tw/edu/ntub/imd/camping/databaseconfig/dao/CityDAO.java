package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.City;
import tw.edu.ntub.imd.camping.databaseconfig.entity.CityId;

import java.util.List;

@Repository
public interface CityDAO extends BaseViewDAO<City, CityId> {
    List<City> findByEnableIsTrue();
}
