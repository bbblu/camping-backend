package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.camping.bean.ContactInformationBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ContactInformationDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ContactInformation;
import tw.edu.ntub.imd.camping.service.ContactInformationService;
import tw.edu.ntub.imd.camping.service.transformer.ContactInformationTransformer;

@Service
public class ContactInformationServiceImpl extends BaseServiceImpl<ContactInformationBean, ContactInformation, Integer> implements ContactInformationService {
    private final ContactInformationDAO contactInformationDAO;
    private final ContactInformationTransformer transformer;

    public ContactInformationServiceImpl(ContactInformationDAO contactInformationDAO, ContactInformationTransformer transformer) {
        super(contactInformationDAO, transformer);
        this.contactInformationDAO = contactInformationDAO;
        this.transformer = transformer;
    }

    @Override
    public ContactInformationBean save(ContactInformationBean contactInformationBean) {
        ContactInformation contactInformation = transformer.transferToEntity(contactInformationBean);
        ContactInformation saveResult = contactInformationDAO.saveAndFlush(contactInformation);
        return transformer.transferToBean(saveResult);
    }
}
