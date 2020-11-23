package tw.edu.ntub.imd.camping.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.service.UserService;

import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/user/manager")
public class UserManagerRouteController {
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
}
