package tw.edu.ntub.imd.camping.databaseconfig.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Year;

@Converter
public class YearToStringConverter implements AttributeConverter<Year, String> {
    @Override
    public String convertToDatabaseColumn(Year attribute) {
        if (attribute == null) {
            return null;
        }
        return String.valueOf(attribute.getValue());
    }

    @Override
    public Year convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Year.parse(dbData);
    }
}
