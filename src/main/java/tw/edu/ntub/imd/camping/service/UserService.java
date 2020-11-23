package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.UserBadRecordBean;
import tw.edu.ntub.imd.camping.bean.UserBean;

import java.util.List;

public interface UserService extends BaseService<UserBean, String> {
    void updatePassword(String account, String oldPassword, String newPassword);

    void updateEnable(String account, boolean isEnable);

    List<UserBadRecordBean> getBadRecord(String account);
}
