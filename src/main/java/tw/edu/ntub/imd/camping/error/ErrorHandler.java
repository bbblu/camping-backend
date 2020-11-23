package tw.edu.ntub.imd.camping.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ErrorHandler {
    boolean isSupportedStatus(HttpStatus status);

    ResponseEntity<String> getJsonResponse(HttpServletRequest request, HttpServletResponse response) throws IOException;

    String getErrorPageUrl(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
