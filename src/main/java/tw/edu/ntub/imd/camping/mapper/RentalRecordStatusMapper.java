package tw.edu.ntub.imd.camping.mapper;

import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.exception.RentalRecordStatusException;

import java.util.EnumSet;

public interface RentalRecordStatusMapper {
    EnumSet<RentalRecordStatus> EMPTY_SET = EnumSet.noneOf(RentalRecordStatus.class);

    RentalRecordStatus getMappedStatus();

    EnumSet<RentalRecordStatus> getCanChangeToStatusSet();

    void validate(RentalRecord record, RentalRecordStatus newStatus) throws RentalRecordStatusException;

    void beforeChange(RentalRecord record, RentalRecordStatus newStatus, Object payload) throws ClassCastException;

    void afterChange(RentalRecord record, RentalRecordStatus originStatus, Object payload) throws ClassCastException;
}
