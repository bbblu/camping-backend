package tw.edu.ntub.imd.camping.util;

import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Assertions;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.User;

@UtilityClass
public class UserAssertUtils {
    public void assertEqual(UserBean expected, UserBean actual) {
        Assertions.assertEquals(expected, actual);
    }

    public void assertEqual(User expected, User actual) {
        Assertions.assertEquals(expected, actual);
    }

    public void assertEqual(User expected, UserBean actual) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getAccount(), actual.getAccount()),
                () -> Assertions.assertEquals(expected.getPassword(), actual.getPassword()),
                () -> Assertions.assertEquals(expected.getRoleId(), actual.getRoleId()),
                () -> Assertions.assertEquals(expected.isEnable(), actual.isEnable()),
                () -> Assertions.assertEquals(expected.getExperience(), actual.getExperience()),
                () -> Assertions.assertEquals(expected.getLastName(), actual.getLastName()),
                () -> Assertions.assertEquals(expected.getFirstName(), actual.getFirstName()),
                () -> Assertions.assertEquals(expected.getGender(), actual.getGender()),
                () -> Assertions.assertEquals(expected.getEmail(), actual.getEmail()),
                () -> Assertions.assertEquals(expected.getAddress(), actual.getAddress()),
                () -> Assertions.assertEquals(expected.getBirthday(), actual.getBirthday()),
                () -> Assertions.assertEquals(expected.getCreateDate(), actual.getCreateDate()),
                () -> Assertions.assertEquals(expected.getLastModifyAccount(), actual.getLastModifyAccount()),
                () -> Assertions.assertEquals(expected.getLastModifyDate(), actual.getLastModifyDate())
        );
    }

    public void assertEqual(UserBean expected, User actual) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getAccount(), actual.getAccount()),
                () -> Assertions.assertEquals(expected.getPassword(), actual.getPassword()),
                () -> Assertions.assertEquals(expected.getRoleId(), actual.getRoleId()),
                () -> Assertions.assertEquals(expected.isEnable(), actual.isEnable()),
                () -> Assertions.assertEquals(expected.getExperience(), actual.getExperience()),
                () -> Assertions.assertEquals(expected.getLastName(), actual.getLastName()),
                () -> Assertions.assertEquals(expected.getFirstName(), actual.getFirstName()),
                () -> Assertions.assertEquals(expected.getGender(), actual.getGender()),
                () -> Assertions.assertEquals(expected.getEmail(), actual.getEmail()),
                () -> Assertions.assertEquals(expected.getAddress(), actual.getAddress()),
                () -> Assertions.assertEquals(expected.getBirthday(), actual.getBirthday()),
                () -> Assertions.assertEquals(expected.getCreateDate(), actual.getCreateDate()),
                () -> Assertions.assertEquals(expected.getLastModifyAccount(), actual.getLastModifyAccount()),
                () -> Assertions.assertEquals(expected.getLastModifyDate(), actual.getLastModifyDate())
        );
    }
}
