package tw.edu.ntub.imd.camping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tw.edu.ntub.imd.camping.config.entrypoint.CustomEntryPoint;
import tw.edu.ntub.imd.camping.config.filter.CustomLoginFilter;
import tw.edu.ntub.imd.camping.config.filter.JwtAuthenticationFilter;
import tw.edu.ntub.imd.camping.config.handler.CustomAuthenticationSuccessHandler;
import tw.edu.ntub.imd.camping.config.handler.CustomerAccessDeniedHandler;
import tw.edu.ntub.imd.camping.config.properties.FileProperties;
import tw.edu.ntub.imd.camping.config.provider.CustomAuthenticationProvider;
import tw.edu.ntub.imd.camping.config.util.JwtUtils;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final String fileUrlName;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Autowired
    public SecurityConfig(
            FileProperties fileProperties,
            UserDetailsService userDetailsService,
            JwtUtils jwtUtils
    ) {
        this.fileUrlName = fileProperties.getName();
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService, passwordEncoder()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 這個表示哪些頁面"不會用到SpringSecurity"，相當於xml中的security="none"
    // 代表在這些連結中會抓不到登入資訊
    // 即SpringContextHolder.getContext() = null
    // 因此這些只能用在靜態資源上
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/doc/**",
                        "/api/**",
                        "/v3/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/csrf",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-resources/**",
                        "/favicon.ico",
                        "/static/**",
                        "/excel/test",
                        String.format("/%s/**", fileUrlName)
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling() // 出錯時的例外處理
                .authenticationEntryPoint(new CustomEntryPoint()) // 未登入處理
                .accessDeniedHandler(new CustomerAccessDeniedHandler()) // 偵測權限不足的處理
                .and()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .userDetailsService(userDetailsService)
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomLoginFilter(authenticationManager(), new CustomAuthenticationSuccessHandler(jwtUtils)), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests() // 設定Requests的權限需求
                .anyRequest() // 表示除了上述請求，都需要權限
                .permitAll()
                .and()
                .formLogin() // 設定Login，如果是用Form表單登入的話
                .loginPage("/login") // 設定Login頁面的URL
                .loginProcessingUrl("/login") // 設定Login動作的URL
                .failureUrl("/login?error") // 設定Login失敗的URL
                .permitAll() // Login不需要權限
                .usernameParameter("account")
                .passwordParameter("password")
                .and()
                .logout() // 設定Logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // 設定Logout URL
                .logoutSuccessUrl("/login") // 設定登出成功後的URL
                .deleteCookies("JSESSIONID")
                .and()
                .sessionManagement() // Session管理
                .sessionFixation() // Session固定ID保護
                .migrateSession() // 每次登入，都會產生新的，並將舊的屬性複製，預設值
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .invalidSessionUrl("/timeout") // Session過期時的URL導向
                .maximumSessions(1) // 設定Session數只能一個
                .expiredUrl("/timeout") // 設定因為再次登入而導致的URL過期的URL導向
        ;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://211.75.1.204:50001",
                "http://140.131.115.147:3000",
                "http://140.131.115.162:3000",
                "http://140.131.115.163:3000"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Collections.singletonList("X-Auth-Token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
