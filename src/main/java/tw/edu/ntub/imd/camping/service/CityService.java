package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.CityBean;

import java.util.List;

public interface CityService extends BaseViewService<CityBean, Integer> {
    List<CityBean> searchAllEnableCity();
}
