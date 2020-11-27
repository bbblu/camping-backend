package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ThirdPartyProductRecordIndexBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.ThirdPartyProductRecordIndex;
import tw.edu.ntub.imd.camping.service.transformer.ThirdPartyProductRecordIndexTransformer;

@Component
public class ThirdPartyProductRecordIndexTransformerImpl
        implements ThirdPartyProductRecordIndexTransformer {
    @NonNull
    @Override
    public ThirdPartyProductRecordIndex transferToEntity(
            @NonNull ThirdPartyProductRecordIndexBean thirdPartyProductRecordIndexBean) {
        return JavaBeanUtils.copy(thirdPartyProductRecordIndexBean, new ThirdPartyProductRecordIndex());
    }

    @NonNull
    @Override
    public ThirdPartyProductRecordIndexBean transferToBean(
            @NonNull ThirdPartyProductRecordIndex thirdPartyProductRecordIndex) {
        return JavaBeanUtils.copy(thirdPartyProductRecordIndex, new ThirdPartyProductRecordIndexBean());
    }
}
