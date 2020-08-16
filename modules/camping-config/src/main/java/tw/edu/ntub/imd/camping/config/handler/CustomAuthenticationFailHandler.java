package tw.edu.ntub.imd.camping.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import tw.edu.ntub.imd.camping.config.util.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        exception.printStackTrace();
        ObjectMapper mapper = new ObjectMapper();
        ResponseUtils.response(
                response,
                401,
                false,
                ResponseUtils.LOGIN_FAIL_ERROR_CODE,
                exception.getMessage(),
                mapper.createObjectNode()
        );
    }
}
