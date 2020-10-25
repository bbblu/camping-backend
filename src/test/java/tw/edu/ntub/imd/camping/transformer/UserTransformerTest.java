package tw.edu.ntub.imd.camping.transformer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Experience;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Gender;

@DisplayName("測試UserTransformer")
public class UserTransformerTest {

    @Test
    @DisplayName("測試transferToBean(User)")
    void testTransferToBean() throws Exception {
        User user = User.builder()
                .account("account")
                .password("password")
                .experience(Experience.VETERAN)
                .lastName("王")
                .firstName("小明")
                .nickName("管理員")
                .gender(Gender.MALE)
                .email("10646000@ntub.edu.tw")
                .address("台北市中正區濟南路一段321號")
                .build();
//        UserTransformer transformer = new UserTransformerImpl();
//        UserBean userBean = transformer.transferToBean(user);
//        UserAssertUtils.assertEqual(user, userBean);
    }

    @Test
    @DisplayName("測試transferToEntity(UserBean)")
    void testTransferToEntity() throws Exception {
        UserBean userBean = UserBean.builder()
                .account("account")
                .password("password")
                .experience(Experience.VETERAN)
                .lastName("王")
                .firstName("小明")
                .nickName("管理員")
                .gender(Gender.MALE)
                .email("10646000@ntub.edu.tw")
                .address("台北市中正區濟南路一段321號")
                .build();
//        UserTransformer transformer = new UserTransformerImpl();
//        User user = transformer.transferToEntity(userBean);
//        UserAssertUtils.assertEqual(userBean, user);
    }
}
