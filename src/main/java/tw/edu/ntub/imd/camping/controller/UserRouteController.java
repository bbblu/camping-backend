package tw.edu.ntub.imd.camping.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/user")
public class UserRouteController {

    @GetMapping(path = "/change-password")
    public ModelAndView forgotPasswordChangePasswordPage() {
        return new ModelAndView("/user/forgot_password_change_password");
    }
}
