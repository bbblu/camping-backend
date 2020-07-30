package tw.edu.ntub.imd.camping.databaseconfig.converter;

import tw.edu.ntub.birc.common.wrapper.date.LocalTimeWrapper;
import tw.edu.ntub.birc.common.wrapper.date.TimeWrapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalTime;

@Converter
public class TimeWrapperConverter implements AttributeConverter<TimeWrapper, LocalTime> {
    @Override
    public LocalTime convertToDatabaseColumn(TimeWrapper attribute) {
        if (attribute == null) {
            return null;
        }
        return LocalTime.of(attribute.getHour(), attribute.getMinute(), attribute.getSecond(), attribute.getMillisecondOfNanosecond());
    }

    @Override
    public TimeWrapper convertToEntityAttribute(LocalTime dbData) {
        if (dbData == null) {
            return null;
        }
        return new LocalTimeWrapper(dbData);
    }
}
