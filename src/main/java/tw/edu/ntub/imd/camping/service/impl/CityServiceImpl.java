package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.camping.bean.CityBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.CityDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.City;
import tw.edu.ntub.imd.camping.databaseconfig.entity.CityId;
import tw.edu.ntub.imd.camping.service.CityService;
import tw.edu.ntub.imd.camping.service.transformer.CityTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl extends BaseViewServiceImpl<CityBean, City, CityId> implements CityService {
    private final CityDAO cityDAO;
    private final CityTransformer transformer;

    public CityServiceImpl(CityDAO cityDAO, CityTransformer transformer) {
        super(cityDAO, transformer);
        this.cityDAO = cityDAO;
        this.transformer = transformer;
    }

    @Override
    public Map<String, List<String>> searchAllEnableCity() {
        return cityDAO.findByEnableIsTrue()
                .stream()
                .collect(Collectors.toMap(
                        City::getName,
                        city -> {
                            List<String> areaNameList = new ArrayList<>();
                            areaNameList.add(city.getAreaName());
                            return areaNameList;
                        },
                        (first, second) -> {
                            first.addAll(second);
                            return first;
                        }
                ));
    }
}
