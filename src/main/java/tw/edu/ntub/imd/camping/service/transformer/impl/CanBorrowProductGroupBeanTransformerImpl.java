package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.CanBorrowProductGroupBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.City;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.CanBorrowProductGroup;
import tw.edu.ntub.imd.camping.service.transformer.CanBorrowProductGroupBeanTransformer;

import javax.annotation.Nonnull;

@Component
public class CanBorrowProductGroupBeanTransformerImpl implements CanBorrowProductGroupBeanTransformer {
    @Nonnull
    @Override
    public CanBorrowProductGroup transferToEntity(@Nonnull CanBorrowProductGroupBean canBorrowProductGroupBean) {
        return JavaBeanUtils.copy(canBorrowProductGroupBean, new CanBorrowProductGroup());
    }

    @Nonnull
    @Override
    public CanBorrowProductGroupBean transferToBean(@Nonnull CanBorrowProductGroup canBorrowProductGroup) {
        CanBorrowProductGroupBean result = JavaBeanUtils.copy(canBorrowProductGroup, new CanBorrowProductGroupBean());
        if (canBorrowProductGroup.getProductType() != null) {
            result.setProductTypeArray(canBorrowProductGroup.getProductType().split(","));
        }
        result.setCity(canBorrowProductGroup.getCityName());
        if (canBorrowProductGroup.getCity() != null) {
            City city = canBorrowProductGroup.getCity();
            result.setCityName(city.getName());
            result.setCityAreaName(city.getAreaName());
        }
        return result;
    }
}
