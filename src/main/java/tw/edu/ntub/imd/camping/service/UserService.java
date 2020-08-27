package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.UserBean;

public interface UserService extends BaseService<UserBean, String> {
    boolean isOldPasswordValid(UserBean userBean, String oldPassword);

    void changePassword(UserBean userBean, String newPassword);
}
