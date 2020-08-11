package tw.edu.ntub.imd.camping.databaseconfig.entity.converter;

import tw.edu.ntub.birc.common.wrapper.date.LocalTimeWrapper;
import tw.edu.ntub.birc.common.wrapper.date.TimeWrapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Converter(autoApply = true)
@SuppressWarnings("unused")
public class TimeWrapperConverter implements AttributeConverter<TimeWrapper, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(TimeWrapper attribute) {
        if (attribute == null) {
            return null;
        }
        return Timestamp.valueOf(LocalDateTime.of(
                LocalDate.MIN,
                LocalTime.of(attribute.getHour(), attribute.getMinute(), attribute.getSecond(), attribute.getMillisecondOfNanosecond())
        ));
    }

    @Override
    public TimeWrapper convertToEntityAttribute(Timestamp dbData) {
        if (dbData == null) {
            return null;
        }
        return new LocalTimeWrapper(dbData.toLocalDateTime().toLocalTime());
    }
}
