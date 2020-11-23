package tw.edu.ntub.imd.camping.factory;

import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.mapper.RentalRecordStatusMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RentalRecordStatusMapperFactoryImpl implements RentalRecordStatusMapperFactory {
    private final Map<RentalRecordStatus, RentalRecordStatusMapper> instanceMap = new HashMap<>();

    public RentalRecordStatusMapperFactoryImpl(List<RentalRecordStatusMapper> mapperList) {
        mapperList.forEach(mapper -> instanceMap.put(mapper.getMappedStatus(), mapper));
    }

    @Override
    public RentalRecordStatusMapper create(RentalRecordStatus status) {
        return instanceMap.get(status);
    }
}
