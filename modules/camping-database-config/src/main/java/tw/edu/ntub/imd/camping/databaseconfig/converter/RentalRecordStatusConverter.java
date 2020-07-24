package tw.edu.ntub.imd.camping.databaseconfig.converter;

import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RentalRecordStatusConverter implements AttributeConverter<RentalRecordStatus, String> {
    @Override
    public String convertToDatabaseColumn(RentalRecordStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.status;
    }

    @Override
    public RentalRecordStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return RentalRecordStatus.getByStatus(dbData);
    }
}
