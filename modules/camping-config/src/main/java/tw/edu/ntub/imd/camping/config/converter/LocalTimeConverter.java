package tw.edu.ntub.imd.camping.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.DateTimeUtils;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.birc.common.wrapper.date.TimeDetail;

import java.time.LocalTime;

@Component
public class LocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(@NonNull String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        TimeDetail timeDetail = DateTimeUtils.parseTime(source);
        return LocalTime.of(
                timeDetail.getHour(),
                timeDetail.getMinute(),
                timeDetail.getSecond(),
                timeDetail.getMillisecondOfNanosecond()
        );
    }
}
