package tw.edu.ntub.imd.camping.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tw.edu.ntub.imd.camping.annotation.DAOTest;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Experience;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Gender;

import java.time.LocalDate;

@DAOTest
@DisplayName("測試UserDAO")
public class UserDAOTest {
    @Autowired
    private UserDAO userDAO;

    @Test
    @DisplayName("測試save(User)")
    void testSave() throws Exception {
        User user = User.builder()
                .account("account")
                .password("password")
                .birthday(LocalDate.now())
                .lastName("姓")
                .firstName("名")
                .nickName("暱稱")
                .email("10646000@ntub.edu.tw")
                .address("地址")
                .gender(Gender.MALE)
                .experience(Experience.ROOKIE)
                .build();
        Assertions.assertDoesNotThrow(() -> userDAO.save(user));
    }
}
