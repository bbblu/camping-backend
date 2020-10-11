package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.MathUtils;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserCommentDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;
import tw.edu.ntub.imd.camping.databaseconfig.entity.UserComment;
import tw.edu.ntub.imd.camping.exception.*;
import tw.edu.ntub.imd.camping.service.UserService;
import tw.edu.ntub.imd.camping.service.transformer.UserTransformer;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserBean, User, String> implements UserService {
    private final UserDAO userDAO;
    private final UserTransformer transformer;
    private final PasswordEncoder passwordEncoder;
    private final UserCommentDAO commentDAO;

    @Autowired
    public UserServiceImpl(
            UserDAO userDAO,
            UserTransformer transformer,
            PasswordEncoder passwordEncoder,
            UserCommentDAO commentDAO) {
        super(userDAO, transformer);
        this.userDAO = userDAO;
        this.transformer = transformer;
        this.passwordEncoder = passwordEncoder;
        this.commentDAO = commentDAO;
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

    @Override
    public void createComment(String account, byte comment) {
        if (MathUtils.isInRange(comment, 1, 5)) {
            if (userDAO.existsById(account)) {
                if (commentDAO.existsByUserAccountAndCommentAccount(account, SecurityUtils.getLoginUserAccount())) {
                    throw new DuplicateCommentException();
                } else {
                    UserComment productGroupComment = new UserComment();
                    productGroupComment.setUserAccount(account);
                    productGroupComment.setComment(comment);
                    commentDAO.save(productGroupComment);
                }
            } else {
                throw new NotFoundException("無此使用者");
            }
        } else {
            throw new InvalidCommentRangeException(comment);
        }
    }
}
