package tw.edu.ntub.imd.camping.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import tw.edu.ntub.birc.common.enumerate.date.Month;
import tw.edu.ntub.birc.common.wrapper.date.DateTimePattern;
import tw.edu.ntub.birc.common.wrapper.date.DateWrapperImpl;
import tw.edu.ntub.imd.camping.TestApplication;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.config.properties.FileProperties;
import tw.edu.ntub.imd.camping.config.util.JwtUtils;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Experience;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Gender;
import tw.edu.ntub.imd.camping.enumerate.ContentType;
import tw.edu.ntub.imd.camping.enumerate.HttpMethod;
import tw.edu.ntub.imd.camping.exception.DuplicateCreateException;
import tw.edu.ntub.imd.camping.request.SpringMockRequestFactory;
import tw.edu.ntub.imd.camping.request.Status;
import tw.edu.ntub.imd.camping.service.UserService;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

import java.util.stream.Stream;

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = TestApplication.class)
@Import(FileProperties.class)
@DisplayName("測試UserController")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private JwtUtils jwtUtils;

    @Test
    @DisplayName("測試符合表單驗證的新增")
    void testNormalCreate() throws Exception {
        Mockito.doReturn(
                createValidUserBeanBuilder()
                        .password("encoded password")
                        .build()
        ).when(userService).save(Mockito.any(UserBean.class));
        SpringMockRequestFactory.getInstance(mockMvc)
                .createJsonRequest("/user", HttpMethod.POST)
                .setBody(userBeanToObjectData(createValidUserBeanBuilder().build()))
                .send()
                .assertStatusEquals(Status.OK)
                .assertContentTypeEquals(ContentType.JSON)
                .assertBodyValueEquals("$.result", true)
                .assertBodyValueIsEmptyString("$.errorCode")
                .assertBodyValueEquals("$.message", "註冊成功")
                .assertBodyValueExist("$.data")
                .assertBodyValueIsObject("$.data")
                .assertBodyValueIsEmpty("$.data");
        Mockito.verify(userService).save(Mockito.any(UserBean.class));
    }

    private static UserBean.UserBeanBuilder createValidUserBeanBuilder() {
        return UserBean.builder()
                .account("test")
                .password("password")
                .experience(Experience.VETERAN)
                .lastName("王")
                .firstName("小明")
                .nickName("管理員")
                .gender(Gender.MALE)
                .email("10646000@ntub.edu.tw")
                .address("台北市中正區濟南路一段321號")
                .birthday(new DateWrapperImpl(1999, Month.APRIL, 3));
    }

    private static ObjectData userBeanToObjectData(UserBean userBean) {
        return new ObjectData()
                .add("account", userBean.getAccount())
                .add("password", userBean.getPassword())
                .add("experience", userBean.getExperience() != null ? userBean.getExperience().id : null)
                .add("name", userBean.getFullName())
                .add("lastName", userBean.getLastName())
                .add("firstName", userBean.getFirstName())
                .add("nickName", userBean.getNickName())
                .add("gender", userBean.getGender() != null ? userBean.getGender().id : null)
                .add("email", userBean.getEmail())
                .add("address", userBean.getAddress())
                .add("birthday", userBean.getBirthday(), DateTimePattern.DEFAULT_DATE);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("測試表單驗證錯誤")
    void testCreateInvalidForm(UserBean.UserBeanBuilder builder, String message) throws Exception {
        SpringMockRequestFactory.getInstance(mockMvc)
                .createJsonRequest("/user", HttpMethod.POST)
                .setBody(userBeanToObjectData(builder.build()))
                .send()
                .assertStatusEquals(Status.OK)
                .assertContentTypeEquals(ContentType.JSON)
                .assertBodyValueEquals("$.result", false)
                .assertBodyValueEquals("$.errorCode", "FormValidation - Invalid")
                .assertBodyValueEquals("$.message", message)
                .assertBodyValueExist("$.data")
                .assertBodyValueIsObject("$.data")
                .assertBodyValueIsEmpty("$.data");
        Mockito.verify(userService, Mockito.times(0)).save(Mockito.any(UserBean.class));
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> testCreateInvalidForm() {
        return Stream.of(
                Arguments.arguments(createValidUserBeanBuilder().account(null), "帳號 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().account(""), "帳號 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().account("測".repeat(101)), "帳號 - 輸入字數大於100個字"),
                Arguments.arguments(createValidUserBeanBuilder().password(null), "密碼 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().password(""), "密碼 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().experience(null), "經驗 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().lastName(null), "姓氏 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().lastName(""), "姓氏 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().lastName("測".repeat(51)), "姓氏 - 輸入字數大於50個字"),
                Arguments.arguments(createValidUserBeanBuilder().firstName(null), "名字 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().firstName(""), "名字 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().firstName("測".repeat(51)), "名字 - 輸入字數大於50個字"),
                Arguments.arguments(createValidUserBeanBuilder().nickName(null), "暱稱 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().nickName(""), "暱稱 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().nickName("測".repeat(51)), "暱稱 - 輸入字數大於50個字"),
                Arguments.arguments(createValidUserBeanBuilder().gender(null), "性別 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().email(null), "信箱 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().email(""), "信箱 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().email("abc"), "信箱 - 格式不符合信箱格式"),
                Arguments.arguments(createValidUserBeanBuilder().address(null), "地址 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().address(""), "地址 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().address("測".repeat(51)), "地址 - 輸入字數大於50個字"),
                Arguments.arguments(createValidUserBeanBuilder().birthday(null), "生日 - 未填寫"),
                Arguments.arguments(createValidUserBeanBuilder().birthday(new DateWrapperImpl(2020, Month.AUGUST, 9)), "生日 - 應為過去日期"),
                Arguments.arguments(createValidUserBeanBuilder().birthday(new DateWrapperImpl(2020, Month.AUGUST, 10)), "生日 - 應為過去日期"),
                Arguments.arguments(createValidUserBeanBuilder().birthday(new DateWrapperImpl(2021, Month.AUGUST, 10)), "生日 - 應為過去日期")
        );
    }

    @Test
    @DisplayName("測試重複註冊應註冊失敗")
    void testRepeatCreate() throws Exception {
        Mockito.doThrow(new DuplicateCreateException("帳號已有人註冊")).when(userService).save(Mockito.any(UserBean.class));
        SpringMockRequestFactory.getInstance(mockMvc)
                .createJsonRequest("/user", HttpMethod.POST)
                .setBody(userBeanToObjectData(createValidUserBeanBuilder().build()))
                .send()
                .assertStatusEquals(Status.OK)
                .assertContentTypeEquals(ContentType.JSON)
                .assertBodyValueEquals("$.result", false)
                .assertBodyValueEquals("$.errorCode", "Create - Duplicate")
                .assertBodyValueEquals("$.message", "帳號已有人註冊")
                .assertBodyValueExist("$.data")
                .assertBodyValueIsObject("$.data")
                .assertBodyValueIsEmpty("$.data");
        Mockito.verify(userService, Mockito.times(1)).save(Mockito.any(UserBean.class));
    }
}
