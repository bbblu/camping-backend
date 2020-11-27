package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.User;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserRoleEnum;

import javax.annotation.Nonnull;
import javax.persistence.PrePersist;

public class UserListener {

    @PrePersist
    public void beforeSave(@Nonnull User user) {
        if (user.getRoleId() == null) {
            user.setRoleId(UserRoleEnum.USER);
        }
        if (user.isEnable() == null) {
            user.setEnable(true);
        }
        if (user.isLocked() == null) {
            user.setLocked(false);
        }
    }
}
