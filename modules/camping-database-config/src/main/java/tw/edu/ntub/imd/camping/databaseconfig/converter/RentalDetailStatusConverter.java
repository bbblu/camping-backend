package tw.edu.ntub.imd.camping.databaseconfig.converter;

import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalDetailStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RentalDetailStatusConverter implements AttributeConverter<RentalDetailStatus, String> {
    @Override
    public String convertToDatabaseColumn(RentalDetailStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.status;
    }

    @Override
    public RentalDetailStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return RentalDetailStatus.getByStatus(dbData);
    }
}
