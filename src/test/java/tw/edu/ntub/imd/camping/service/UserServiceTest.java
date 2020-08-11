package tw.edu.ntub.imd.camping.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import tw.edu.ntub.birc.common.enumerate.date.Month;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.birc.common.wrapper.date.DateWrapperImpl;
import tw.edu.ntub.imd.camping.TestApplication;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.config.properties.FileProperties;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Experience;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Gender;
import tw.edu.ntub.imd.camping.service.transformer.UserTransformer;
import tw.edu.ntub.imd.camping.util.UserAssertUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
@Import(FileProperties.class)
@DisplayName("測試UserService")
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserDAO userDAO;
    @MockBean
    private UserTransformer userTransformer;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("測試正常新增而不會拋出例外")
    void testNormalSave() throws Exception {
        Mockito.doReturn("encoded password").when(passwordEncoder).encode(Mockito.anyString());
        UserBean userBean = UserBean.builder()
                .account("test")
                .password("password")
                .experience(Experience.VETERAN)
                .lastName("王")
                .firstName("小明")
                .nickName("administrator")
                .gender(Gender.MALE)
                .email("10646000@ntub.edu.tw")
                .address("台北市中正區濟南路一段321號")
                .birthday(new DateWrapperImpl(1999, Month.APRIL, 3))
                .lastModifyAccount("test")
                .build();
        User saveUser = JavaBeanUtils.copy(userBean, new User());
        LocalDateTime createDate = LocalDateTime.of(
                LocalDate.of(2020, 7, 28),
                LocalTime.of(16, 29, 49, 812_000_000)
        );
        saveUser.setPassword(passwordEncoder.encode("password"));
        saveUser.setCreateDate(createDate);
        saveUser.setLastModifyDate(createDate);
        Mockito.doReturn(saveUser).when(userDAO).save(Mockito.any(User.class));
        Mockito.doReturn(JavaBeanUtils.copy(userBean, new User()))
                .when(userTransformer).transferToEntity(Mockito.any(UserBean.class));
        Mockito.doReturn(JavaBeanUtils.copy(saveUser, new UserBean()))
                .when(userTransformer).transferToBean(Mockito.any(User.class));

        UserBean saveResult = userService.save(userBean);
        UserAssertUtils.assertEqual(saveUser, saveResult);
    }
}
