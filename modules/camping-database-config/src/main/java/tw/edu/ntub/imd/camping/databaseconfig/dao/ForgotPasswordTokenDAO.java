package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ForgotPasswordToken;

import java.util.Optional;

@Repository
public interface ForgotPasswordTokenDAO extends BaseDAO<ForgotPasswordToken, Integer> {
    @Query("SELECT t FROM ForgotPasswordToken t WHERE t.token = :token AND t.enable = true AND CURRENT_TIMESTAMP < t.expireDate")
    Optional<ForgotPasswordToken> findValidToken(@Param("token") String token);
}
