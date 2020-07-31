package tw.edu.ntub.imd.camping.config.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("spring.security.jwt.secret")
    private String secret;

    public String getToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject("Login")
                .setIssuer(userDetails.getUsername())
                .setIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .claim(
                        "roles",
                        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new)
                )
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @SuppressWarnings("unchecked")
    public Authentication getAuthentication(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        List<String> roles = (List<String>) body.get("roles");
        return new UsernamePasswordAuthenticationToken(
                body.getIssuer(),
                null,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }
}
