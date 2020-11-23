package tw.edu.ntub.imd.camping.service;

import tw.edu.ntub.imd.camping.bean.NotificationBean;

import java.util.List;

public interface NotificationService extends BaseViewService<NotificationBean, Integer> {
    long getNotReadCount(String userAccount);

    List<NotificationBean> searchByUserAccount(String userAccount);
}
