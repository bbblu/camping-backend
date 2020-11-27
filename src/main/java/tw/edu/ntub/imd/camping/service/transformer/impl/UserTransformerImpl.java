package tw.edu.ntub.imd.camping.service.transformer.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.ForgotPasswordBean;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserCompensateRecordDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;
import tw.edu.ntub.imd.camping.service.transformer.UserTransformer;

import javax.annotation.Nonnull;

@AllArgsConstructor
@Component
public class UserTransformerImpl implements UserTransformer {
    private final UserCompensateRecordDAO compensateRecordDAO;

    @Nonnull
    @Override
    public User transferToEntity(@Nonnull UserBean userBean) {
        return JavaBeanUtils.copy(userBean, new User());
    }

    @Nonnull
    @Override
    public UserBean transferToBean(@Nonnull User user) {
        UserBean result = JavaBeanUtils.copy(user, new UserBean());
        if (StringUtils.isNotBlank(user.getAccount())) {
            result.setNotCompensate(
                    compensateRecordDAO.existsByUserAccountAndCompensatedIsFalse(user.getAccount())
            );
        }
        return result;
    }

    @Override
    public User transferForgotPasswordBeanToEntity(ForgotPasswordBean forgotPasswordBean) {
        return User.builder()
                .account(forgotPasswordBean.getAccount())
                .email(forgotPasswordBean.getEmail())
                .cellPhone(forgotPasswordBean.getCellPhone())
                .build();
    }
}
