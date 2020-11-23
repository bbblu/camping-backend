package tw.edu.ntub.imd.camping.mapper;

import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.exception.RentalRecordStatusException;

import java.util.EnumSet;

@Component
public class RentalRecordTerminateMapper implements RentalRecordStatusMapper {
    private static final EnumSet<RentalRecordStatus> CAN_CHANGE_TO_STATUS_SET = EnumSet.of(
            RentalRecordStatus.ALREADY_TERMINATED
    );

    @Override
    public RentalRecordStatus getMappedStatus() {
        return RentalRecordStatus.TERMINATE;
    }

    @Override
    public EnumSet<RentalRecordStatus> getCanChangeToStatusSet() {
        return CAN_CHANGE_TO_STATUS_SET;
    }

    @Override
    public void validate(RentalRecord record, RentalRecordStatus newStatus) throws RentalRecordStatusException {

    }

    @Override
    public void beforeChange(RentalRecord record, RentalRecordStatus newStatus, Object payload) throws ClassCastException {

    }

    @Override
    public void afterChange(RentalRecord record, RentalRecordStatus originStatus, Object payload) throws ClassCastException {

    }
}
