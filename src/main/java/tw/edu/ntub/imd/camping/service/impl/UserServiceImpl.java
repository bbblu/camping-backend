package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;
import tw.edu.ntub.imd.camping.exception.DuplicateCreateException;
import tw.edu.ntub.imd.camping.exception.NotAccountOwnerException;
import tw.edu.ntub.imd.camping.service.UserService;
import tw.edu.ntub.imd.camping.service.transformer.UserTransformer;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserBean, User, String> implements UserService {
    private final UserDAO userDAO;
    private final UserTransformer transformer;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserTransformer transformer, PasswordEncoder passwordEncoder) {
        super(userDAO, transformer);
        this.userDAO = userDAO;
        this.transformer = transformer;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserBean save(UserBean userBean) {
        try {
            User user = transformer.transferToEntity(userBean);
            user.setPassword(passwordEncoder.encode(userBean.getPassword()));
            user.setLastModifyAccount(userBean.getAccount());
            User saveResult = userDAO.saveAndFlush(user);
            return transformer.transferToBean(saveResult);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateCreateException(userBean.getAccount() + "帳號已有人註冊");
        }
    }

    @Override
    public void update(String account, UserBean userBean) {
        if (StringUtils.isEquals(account, SecurityUtils.getLoginUserAccount())) {
            throw new NotAccountOwnerException();
        } else {
            super.update(account, userBean);
        }
    }
}
