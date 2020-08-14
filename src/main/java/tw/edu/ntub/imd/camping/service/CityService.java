package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.CityBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.CityId;

import java.util.List;
import java.util.Map;

public interface CityService extends BaseViewService<CityBean, CityId> {
    Map<String, List<String>> searchAllEnableCity();
}
