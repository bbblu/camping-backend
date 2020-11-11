package tw.edu.ntub.imd.camping.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.exception.PermissionDeniedException;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedErrorHandler implements ErrorHandler {
    @Override
    public boolean isSupportedStatus(HttpStatus status) {
        return status == HttpStatus.FORBIDDEN;
    }

    @Override
    public ResponseEntity<String> getJsonResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntityBuilder.error(new PermissionDeniedException()).build();
    }

    @Override
    public String getErrorPageUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "/error403";
    }
}
