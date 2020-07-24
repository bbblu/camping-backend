package tw.edu.ntub.imd.camping.config.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tw.edu.ntub.imd.camping.config.handler.CustomAuthenticationFailHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    public CustomLoginFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler authenticationSuccessHandler) {
        setUsernameParameter("account");
        setPasswordParameter("password");
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(new CustomAuthenticationFailHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getMethod().equalsIgnoreCase("POST")) {
            String account = obtainUsername(request);
            String password = obtainPassword(request);
            UsernamePasswordAuthenticationToken authentication;
            if (request.getContentType().startsWith("application/json")) {
                authentication = resolveAuthenticationFromRequestBody(request);
            } else {
                authentication = new UsernamePasswordAuthenticationToken(account, password);
            }
            setDetails(request, authentication);
            return getAuthenticationManager().authenticate(authentication);
        } else {
            throw new AuthenticationServiceException("認證方法不支援：" + request.getMethod());
        }
    }

    private UsernamePasswordAuthenticationToken resolveAuthenticationFromRequestBody(HttpServletRequest request) throws AuthenticationException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            JsonNode loginRequest = mapper.readTree(stringBuilder.toString());
            return new UsernamePasswordAuthenticationToken(
                    loginRequest.findValue("account").asText(),
                    loginRequest.findValue("password").asText()
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("登入失敗");
        }
    }
}
