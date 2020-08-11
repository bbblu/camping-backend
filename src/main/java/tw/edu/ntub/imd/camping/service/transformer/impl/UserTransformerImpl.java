package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;
import tw.edu.ntub.imd.camping.service.transformer.UserTransformer;

import javax.annotation.Nonnull;

@Component
public class UserTransformerImpl implements UserTransformer {
    @Nonnull
    @Override
    public User transferToEntity(@Nonnull UserBean userBean) {
        return JavaBeanUtils.copy(userBean, new User());
    }

    @Nonnull
    @Override
    public UserBean transferToBean(@Nonnull User user) {
        return JavaBeanUtils.copy(user, new UserBean());
    }
}
