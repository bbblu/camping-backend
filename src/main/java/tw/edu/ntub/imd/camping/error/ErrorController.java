package tw.edu.ntub.imd.camping.error;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@AllArgsConstructor
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private final Set<ErrorHandler> errorHandlerSet;

    @RequestMapping(path = "/error")
    public void errorHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contentType = request.getContentType();
        HttpStatus status = HttpStatus.resolve(response.getStatus());
        ErrorHandler errorHandler = errorHandlerSet.stream()
                .filter(handler -> handler.isSupportedStatus(status))
                .findAny()
                .orElse(new InternalServerErrorHandler());
        if (contentType != null && contentType.startsWith("application/json")) {
            response.resetBuffer();
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ResponseEntity<String> responseEntity = errorHandler.getJsonResponse(request, response);
            PrintWriter output = response.getWriter();
            output.println(responseEntity.getBody());
            output.flush();
        } else {
            response.sendRedirect(errorHandler.getErrorPageUrl(request, response));
        }
    }

    @GetMapping(path = "/error403")
    public ModelAndView error403Page() {
        return new ModelAndView("/error403");
    }

    @GetMapping(path = "/error404")
    public ModelAndView error404Page() {
        return new ModelAndView("/error404");
    }

    @GetMapping(path = "/error500")
    public ModelAndView error500Page() {
        return new ModelAndView("/error500");
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
