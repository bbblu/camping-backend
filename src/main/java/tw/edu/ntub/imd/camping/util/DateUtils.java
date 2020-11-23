package tw.edu.ntub.imd.camping.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@UtilityClass
public class DateUtils {

    public Date convertLocalDateTimeToDate(@NonNull LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
