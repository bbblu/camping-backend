package tw.edu.ntub.imd.camping.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.edu.ntub.imd.camping.service.ProblemReportService;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/problem-report/manager")
public class ProblemReportManagerRouteController {
    private static final int PROBLEM_REPORT_PER_PAGE = 10;
    private final ProblemReportService problemReportService;

    @GetMapping(path = "")
    @PreAuthorize("hasAnyAuthority('Administrator', 'Manager')")
    public ModelAndView indexPage() {
        ModelAndView modelAndView = new ModelAndView("/problem-report");
        modelAndView.addObject(
                "pageInfo",
                problemReportService.getPageInfo(PROBLEM_REPORT_PER_PAGE)
        );
        modelAndView.addObject(
                "problemReportList",
                problemReportService.searchAll()
        );
        return modelAndView;
    }
}
