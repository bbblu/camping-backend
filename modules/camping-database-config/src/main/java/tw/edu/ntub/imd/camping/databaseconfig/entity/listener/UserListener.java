package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserRoleEnum;

import javax.annotation.Nonnull;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class UserListener {

    @PrePersist
    public void beforeSave(@Nonnull User user) {
        if (user.getRoleId() == null) {
            user.setRoleId(UserRoleEnum.USER);
        }
        if (user.isEnable() == null) {
            user.setEnable(true);
        }
        if (user.getCreateDate() == null) {
            user.setCreateDate(LocalDateTime.now());
        }
        if (user.getLastModifyDate() == null) {
            user.setLastModifyDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void beforeUpdate(@Nonnull User user) {
        user.setLastModifyAccount(SecurityUtils.getLoginUserAccount());
        user.setLastModifyDate(LocalDateTime.now());
    }
}
