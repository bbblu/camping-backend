package tw.edu.ntub.imd.camping.bean;

import lombok.Data;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserBadRecordType;

@Data
public class UserBadRecordBean {
    private UserBadRecordType type;
    private Long count;
}
