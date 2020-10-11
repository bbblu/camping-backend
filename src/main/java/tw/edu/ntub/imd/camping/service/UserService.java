package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.UserBean;

public interface UserService extends BaseService<UserBean, String> {
    void createComment(String account, byte comment);
}
