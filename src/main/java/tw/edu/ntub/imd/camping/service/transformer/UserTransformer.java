package tw.edu.ntub.imd.camping.service.transformer;

import tw.edu.ntub.imd.camping.bean.ForgotPasswordBean;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;

public interface UserTransformer extends BeanEntityTransformer<UserBean, User> {
    User transferForgotPasswordBeanToEntity(ForgotPasswordBean forgotPasswordBean);
}
