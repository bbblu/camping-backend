package tw.edu.ntub.imd.camping.databaseconfig.converter;

import tw.edu.ntub.imd.camping.databaseconfig.enumerate.LogRecordDeviceType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LogRecordDeviceTypeConverter implements AttributeConverter<LogRecordDeviceType, String> {
    @Override
    public String convertToDatabaseColumn(LogRecordDeviceType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.deviceType;
    }

    @Override
    public LogRecordDeviceType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return LogRecordDeviceType.getInstance(dbData);
    }
}
