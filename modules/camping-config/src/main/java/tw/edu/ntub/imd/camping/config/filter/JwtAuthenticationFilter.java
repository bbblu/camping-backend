package tw.edu.ntub.imd.camping.config.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.config.util.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Logger log = LogManager.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtils jwtUtils;

    @Autowired
    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws IOException, ServletException {
        String token = getToken(request);
        if (StringUtils.isNotBlank(token)) {
            try {
                Authentication authentication = jwtUtils.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException |
                    UnsupportedJwtException |
                    MalformedJwtException |
                    SignatureException |
                    IllegalArgumentException e) {
                log.error("JWT解析錯誤", e);
            }
        }
        try {
            chain.doFilter(request, response);
        } catch (InsufficientAuthenticationException e) {
            log.error("請求方法：" + request.getMethod().toUpperCase() + "請求URL：" + request.getRequestURL(), e);
        }
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String headerToken = header != null ? header.replaceFirst("Bearer ", "") : null;
        String cookieToken = Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[0])
                .filter(cookie -> StringUtils.isEquals(cookie.getName(), "X-Auth-Token"))
                .findAny()
                .map(Cookie::getValue)
                .orElse("");
        return StringUtils.isNotBlank(headerToken) ? headerToken : cookieToken;
    }
}
