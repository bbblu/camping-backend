package tw.edu.ntub.imd.camping.databaseconfig.entity.listener;

import tw.edu.ntub.imd.camping.databaseconfig.entity.ForgotPasswordToken;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ForgotPasswordTokenListener {

    @PrePersist
    public void preSave(ForgotPasswordToken forgotPasswordToken) {
        if (forgotPasswordToken.isEnable() == null) {
            forgotPasswordToken.setEnable(true);
        }
        if (forgotPasswordToken.getToken() == null) {
            forgotPasswordToken.setToken(UUID.randomUUID().toString());
        }
        if (forgotPasswordToken.getCreateDate() != null) {
            forgotPasswordToken.setExpireDate(forgotPasswordToken.getCreateDate().plusDays(7));
        }
    }

    @PreUpdate
    public void preUpdate(ForgotPasswordToken forgotPasswordToken) {
        forgotPasswordToken.setDisableDate(LocalDateTime.now());
    }
}
