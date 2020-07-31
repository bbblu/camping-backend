package tw.edu.ntub.imd.camping.databaseconfig.converter;

import tw.edu.ntub.birc.common.wrapper.date.DateWrapper;
import tw.edu.ntub.birc.common.wrapper.date.LocalDateWrapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;

@Converter
public class DateWrapperConverter implements AttributeConverter<DateWrapper, LocalDate> {
    @Override
    public LocalDate convertToDatabaseColumn(DateWrapper attribute) {
        if (attribute == null) {
            return null;
        }
        return LocalDate.of(attribute.getYear(), attribute.getMonthNo(), attribute.getDay());
    }

    @Override
    public DateWrapper convertToEntityAttribute(LocalDate dbData) {
        if (dbData == null) {
            return null;
        }
        return new LocalDateWrapper(dbData);
    }
}
