package tw.edu.ntub.imd.camping.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.config.util.JwtUtils;
import tw.edu.ntub.imd.camping.config.util.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtils jwtUtils;

    @Autowired
    public CustomAuthenticationSuccessHandler(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.getToken(userDetails);
        response.setHeader("X-Auth-Token", token);
        response.addCookie(new Cookie("X-Auth-Token", token));
        ObjectMapper mapper = new ObjectMapper();
        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) authentication.getAuthorities();
        GrantedAuthority grantedAuthority = authorities.get(0);
        String authorityName = grantedAuthority.getAuthority();
        ResponseUtils.response(
                response,
                200,
                true,
                ResponseUtils.LOGIN_SUCCESS_ERROR_CODE,
                ResponseUtils.LOGIN_SUCCESS_MESSAGE,
                mapper.createObjectNode().put("identity", authorityName)
        );
    }
}
