package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;
import tw.edu.ntub.imd.camping.exception.DuplicateCreateException;
import tw.edu.ntub.imd.camping.exception.InvalidOldPasswordException;
import tw.edu.ntub.imd.camping.exception.NotFoundException;
import tw.edu.ntub.imd.camping.service.UserService;
import tw.edu.ntub.imd.camping.service.transformer.UserTransformer;

import java.util.Optional;

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
            User saveResult = userDAO.saveAndFlush(user);
            return transformer.transferToBean(saveResult);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateCreateException(userBean.getAccount() + "帳號已有人註冊");
        }
    }

    @Override
    public void changePassword(String account, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userDAO.findById(account);
        User user = optionalUser.orElseThrow(() -> new NotFoundException("無此使用者"));

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userDAO.save(user);
        } else {
            throw new InvalidOldPasswordException();
        }
    }
}
