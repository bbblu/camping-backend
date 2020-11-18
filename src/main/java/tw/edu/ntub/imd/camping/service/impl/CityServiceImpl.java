package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.imd.camping.bean.CityBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.CityDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.City;
import tw.edu.ntub.imd.camping.service.CityService;
import tw.edu.ntub.imd.camping.service.transformer.CityTransformer;

import java.util.List;

@Service
public class CityServiceImpl extends BaseViewServiceImpl<CityBean, City, Integer> implements CityService {
    private final CityDAO cityDAO;
    private final CityTransformer transformer;

    public CityServiceImpl(CityDAO cityDAO, CityTransformer transformer) {
        super(cityDAO, transformer);
        this.cityDAO = cityDAO;
        this.transformer = transformer;
    }

    @Override
    public List<CityBean> searchAllEnableCity() {
        return CollectionUtils.map(cityDAO.findByEnableIsTrue(), transformer::transferToBean);
    }
}
