package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.ForgotPasswordBean;
import tw.edu.ntub.imd.camping.bean.UserBadRecordBean;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.*;
import tw.edu.ntub.imd.camping.databaseconfig.entity.*;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserBadRecordType;
import tw.edu.ntub.imd.camping.dto.BankAccount;
import tw.edu.ntub.imd.camping.dto.CreditCard;
import tw.edu.ntub.imd.camping.dto.Mail;
import tw.edu.ntub.imd.camping.exception.DuplicateCreateException;
import tw.edu.ntub.imd.camping.exception.InvalidOldPasswordException;
import tw.edu.ntub.imd.camping.exception.NotAccountOwnerException;
import tw.edu.ntub.imd.camping.exception.NotFoundException;
import tw.edu.ntub.imd.camping.service.UserService;
import tw.edu.ntub.imd.camping.service.transformer.UserTransformer;
import tw.edu.ntub.imd.camping.util.MailSender;
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
    private final MailSender mailSender;
    private final ForgotPasswordTokenDAO forgotPasswordTokenDAO;

    @Autowired
    public UserServiceImpl(
            UserDAO userDAO,
            UserTransformer transformer,
            PasswordEncoder passwordEncoder,
            TransactionUtils transactionUtils,
            UserBadRecordDAO badRecordDAO,
            UserCommentDAO commentDAO,
            UserCompensateRecordDAO compensateRecordDAO,
            MailSender mailSender,
            ForgotPasswordTokenDAO forgotPasswordTokenDAO) {
        super(userDAO, transformer);
        this.userDAO = userDAO;
        this.transformer = transformer;
        this.passwordEncoder = passwordEncoder;
        this.transactionUtils = transactionUtils;
        this.badRecordDAO = badRecordDAO;
        this.commentDAO = commentDAO;
        this.compensateRecordDAO = compensateRecordDAO;
        this.mailSender = mailSender;
        this.forgotPasswordTokenDAO = forgotPasswordTokenDAO;
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

    @Override
    public void forgotPassword(ForgotPasswordBean forgotPasswordBean) {
        User user = transformer.transferForgotPasswordBeanToEntity(forgotPasswordBean);
        if (userDAO.exists(Example.of(user))) {
            ForgotPasswordToken token = new ForgotPasswordToken();
            token.setUserAccount(user.getAccount());
            ForgotPasswordToken saveResult = forgotPasswordTokenDAO.saveAndFlush(token);

            Mail mail = new Mail("/mail/forgot_password");
            mail.addSendTo(user.getEmail());
            mail.setSubject("借借露 - 忘記密碼");
            mail.addAttribute("token", saveResult);
            mailSender.sendMail(mail);
        } else {
            throw new NotFoundException("無此使用者");
        }
    }

    @Override
    public void updatePasswordForForgotPassword(String token, String newPassword) {
        ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenDAO.findValidToken(token)
                .orElseThrow(() -> new NotFoundException("沒有此驗證碼或驗證碼已過期"));
        User user = forgotPasswordToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userDAO.updateAndFlush(user);
        forgotPasswordToken.setEnable(false);
        forgotPasswordTokenDAO.update(forgotPasswordToken);
    }
}
