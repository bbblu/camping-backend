package tw.edu.ntub.imd.camping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;

@Controller
public class RouteController {

    @GetMapping(path = "")
    public ModelAndView loginPage() {
        if (SecurityUtils.isLogin()) {
            return new ModelAndView("redirect:/problem-report/manager");
        } else {
            return new ModelAndView("/login");
        }
    }
}
