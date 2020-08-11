package tw.edu.ntub.imd.camping.databaseconfig.entity.converter;

import tw.edu.ntub.birc.common.wrapper.date.DateWrapper;
import tw.edu.ntub.birc.common.wrapper.date.LocalDateWrapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Converter(autoApply = true)
@SuppressWarnings("unused")
public class DateWrapperConverter implements AttributeConverter<DateWrapper, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(DateWrapper attribute) {
        if (attribute == null) {
            return null;
        }
        return Timestamp.valueOf(LocalDateTime.of(
                LocalDate.of(attribute.getYear(), attribute.getMonthNo(), attribute.getDay()), LocalTime.MIN
        ));
    }

    @Override
    public DateWrapper convertToEntityAttribute(Timestamp dbData) {
        if (dbData == null) {
            return null;
        }
        return new LocalDateWrapper(dbData.toLocalDateTime().toLocalDate());
    }
}
