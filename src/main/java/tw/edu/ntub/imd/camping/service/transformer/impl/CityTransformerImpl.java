package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.CityBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.City;
import tw.edu.ntub.imd.camping.service.transformer.CityTransformer;

import javax.annotation.Nonnull;

@Component
public class CityTransformerImpl implements CityTransformer {
    @Nonnull
    @Override
    public City transferToEntity(@Nonnull CityBean cityBean) {
        return JavaBeanUtils.copy(cityBean, new City());
    }

    @Nonnull
    @Override
    public CityBean transferToBean(@Nonnull City city) {
        return JavaBeanUtils.copy(city, new CityBean());
    }
}
