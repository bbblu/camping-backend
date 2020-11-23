package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.UserBadRecordBean;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserBadRecordDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserCommentDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserCompensateRecordDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;
import tw.edu.ntub.imd.camping.databaseconfig.entity.UserCompensateRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserBadRecordType;
import tw.edu.ntub.imd.camping.dto.BankAccount;
import tw.edu.ntub.imd.camping.dto.CreditCard;
import tw.edu.ntub.imd.camping.exception.DuplicateCreateException;
import tw.edu.ntub.imd.camping.exception.InvalidOldPasswordException;
import tw.edu.ntub.imd.camping.exception.NotAccountOwnerException;
import tw.edu.ntub.imd.camping.exception.NotFoundException;
import tw.edu.ntub.imd.camping.service.UserService;
import tw.edu.ntub.imd.camping.service.transformer.UserTransformer;
import tw.edu.ntub.imd.camping.util.TransactionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserBean, User, String> implements UserService {
    private final UserDAO userDAO;
    private final UserTransformer transformer;
    private final PasswordEncoder passwordEncoder;
    private final TransactionUtils transactionUtils;
    private final UserBadRecordDAO badRecordDAO;
    private final UserCommentDAO commentDAO;
    private final UserCompensateRecordDAO compensateRecordDAO;

    @Autowired
    public UserServiceImpl(
            UserDAO userDAO,
            UserTransformer transformer,
            PasswordEncoder passwordEncoder,
            TransactionUtils transactionUtils,
            UserBadRecordDAO badRecordDAO,
            UserCommentDAO commentDAO,
            UserCompensateRecordDAO compensateRecordDAO) {
        super(userDAO, transformer);
        this.userDAO = userDAO;
        this.transformer = transformer;
        this.passwordEncoder = passwordEncoder;
        this.transactionUtils = transactionUtils;
        this.badRecordDAO = badRecordDAO;
        this.commentDAO = commentDAO;
        this.compensateRecordDAO = compensateRecordDAO;
    }

    @Override
    public UserBean save(UserBean userBean) {
        try {
            User user = transformer.transferToEntity(userBean);
            user.setPassword(passwordEncoder.encode(userBean.getPassword()));
            user.setLastModifyAccount(user.getAccount());
            User saveResult = userDAO.saveAndFlush(user);
            transactionUtils.createBankAccount(new BankAccount(user.getBankAccount()));
            return transformer.transferToBean(saveResult);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateCreateException(userBean.getAccount() + "帳號已有人註冊");
        }
    }

    @Override
    public void update(String account, UserBean userBean) {
        if (StringUtils.isNotEquals(account, SecurityUtils.getLoginUserAccount())) {
            throw new NotAccountOwnerException();
        } else {
            super.update(account, userBean);
        }
    }

    @Override
    public void updatePassword(String account, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userDAO.findById(account);
        User user = optionalUser.orElseThrow(() -> new NotFoundException("無此使用者"));

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userDAO.save(user);
        } else {
            throw new InvalidOldPasswordException();
        }
    }

    @Override
    public void updateEnable(String account, boolean isEnable) {
        User user = userDAO.findById(account).orElseThrow(() -> new NotFoundException("找不到此使用者：" + account));
        user.setEnable(isEnable);
        userDAO.update(user);
    }

    @Override
    public List<UserBadRecordBean> getBadRecord(String account) {
        List<UserBadRecordBean> result = new ArrayList<>();
        for (Object[] row : badRecordDAO.findBadRecordCount(account)) {
            UserBadRecordBean userBadRecordBean = new UserBadRecordBean();
            userBadRecordBean.setType((UserBadRecordType) row[0]);
            userBadRecordBean.setCount((Long) row[1]);
            result.add(userBadRecordBean);
        }
        return result;
    }

    @Override
    public int getComment(String account) {
        return (int) commentDAO.getAverageCommentByUserAccount(account);
    }

    @Override
    public void compensate(String account, CreditCard creditCard) {
        UserCompensateRecord compensateRecord =
                compensateRecordDAO.findByUserAccountAndCompensatedIsFalse(account)
                        .orElseThrow(() -> new NotFoundException("沒有需要賠償的租借紀錄"));
        RentalRecord rentalRecord = compensateRecord.getRentalRecord();
        ProductGroup productGroup = rentalRecord.getProductGroupByProductGroupId();
        User productOwner = productGroup.getUserByCreateAccount();
        int transactionId = transactionUtils.createTransaction(
                creditCard,
                productOwner.getBankAccount(),
                compensateRecord.getCompensatePrice()
        );
        compensateRecord.setCompensated(true);
        compensateRecord.setTransactionId(transactionId);
        compensateRecord.setCompensateDate(LocalDateTime.now());
        compensateRecordDAO.update(compensateRecord);
    }
}
