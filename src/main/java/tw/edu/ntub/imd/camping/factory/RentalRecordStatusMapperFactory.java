package tw.edu.ntub.imd.camping.factory;

import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.mapper.RentalRecordStatusMapper;

public interface RentalRecordStatusMapperFactory {
    RentalRecordStatusMapper create(RentalRecordStatus status);
}
