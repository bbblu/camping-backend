package tw.edu.ntub.imd.camping.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.exception.NotFoundException;
import tw.edu.ntub.imd.camping.service.UserService;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/user/manager")
@PreAuthorize("hasAnyAuthority('Administrator', 'Manager')")
public class UserManagerController {
    private final UserService userService;

    @GetMapping(path = "")
    public ModelAndView indexPage() {
        ModelAndView modelAndView = new ModelAndView("/user/index");
        modelAndView.addObject("userList", userService.searchAll()
                .stream()
                .filter(user -> StringUtils.isNotEquals(user.getAccount(), "admin"))
                .filter(user -> StringUtils.isNotEquals(user.getAccount(), "anonymousUser"))
                .peek(user -> user.setPassword(""))
                .collect(Collectors.toList())
        );
        return modelAndView;
    }

    @GetMapping(path = "/{account}")
    public ResponseEntity<String> getDetail(@PathVariable String account) {
        UserBean user = userService.getById(account).orElseThrow(() -> new NotFoundException("找不到此使用者"));
        ObjectData userData = new ObjectData();
        userData.add("account", user.getAccount());
        userData.add("enable", user.isEnable());
        userData.add("roleName", user.getRoleId().toString());
        userData.add("gender", user.getGender().name);
        userData.add("lastName", user.getLastName());
        userData.add("firstName", user.getFirstName());
        userData.add("nickName", user.getNickName());
        userData.add("birthday", user.getBirthday());
        userData.add("address", user.getAddress());
        userData.add("cellPhone", user.getCellPhone());
        userData.add("email", user.getEmail());
        return ResponseEntityBuilder.success("查詢成功")
                .data(userData)
                .build();
    }
}
