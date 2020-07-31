package tw.edu.ntub.imd.camping.databaseconfig.converter;

import tw.edu.ntub.birc.common.wrapper.date.DateTimeWrapper;
import tw.edu.ntub.birc.common.wrapper.date.LocalDateTimeWrapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Converter
public class DateTimeWrapperConverter implements AttributeConverter<DateTimeWrapper, LocalDateTime> {
    @Override
    public LocalDateTime convertToDatabaseColumn(DateTimeWrapper attribute) {
        if (attribute == null) {
            return null;
        }
        return LocalDateTime.of(
                LocalDate.of(attribute.getYear(), attribute.getMonthNo(), attribute.getDay()),
                LocalTime.of(attribute.getHour(), attribute.getMinute(), attribute.getSecond(), attribute.getMillisecondOfNanosecond())
        );
    }

    @Override
    public DateTimeWrapper convertToEntityAttribute(LocalDateTime dbData) {
        if (dbData == null) {
            return null;
        }
        return new LocalDateTimeWrapper(dbData);
    }
}
