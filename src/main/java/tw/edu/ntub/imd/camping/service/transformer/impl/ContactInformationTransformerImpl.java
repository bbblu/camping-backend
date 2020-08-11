package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ContactInformationBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ContactInformation;
import tw.edu.ntub.imd.camping.service.transformer.ContactInformationTransformer;

import javax.annotation.Nonnull;

@Component
public class ContactInformationTransformerImpl implements ContactInformationTransformer {
    @Nonnull
    @Override
    public ContactInformation transferToEntity(@Nonnull ContactInformationBean contactInformationBean) {
        return JavaBeanUtils.copy(contactInformationBean, new ContactInformation());
    }

    @Nonnull
    @Override
    public ContactInformationBean transferToBean(@Nonnull ContactInformation contactInformation) {
        return JavaBeanUtils.copy(contactInformation, new ContactInformationBean());
    }
}
