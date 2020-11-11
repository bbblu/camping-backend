package tw.edu.ntub.imd.camping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RouteController {

    @GetMapping(path = "")
    public ModelAndView loginPage() {
        return new ModelAndView("/login");
    }
}
