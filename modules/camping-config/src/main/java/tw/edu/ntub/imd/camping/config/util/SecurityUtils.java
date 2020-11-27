package tw.edu.ntub.imd.camping.config.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import tw.edu.ntub.birc.common.util.BooleanUtils;
import tw.edu.ntub.birc.common.util.StringUtils;

public final class SecurityUtils {
    private SecurityUtils() {

    }

    public static String getLoginUserAccount() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean isNotLogin() {
        return BooleanUtils.isFalse(isLogin());
    }

    public static boolean isLogin() {
        return StringUtils.isNotEquals(getLoginUserAccount(), "anonymousUser");
    }

    public static boolean isNotManager() {
        return !isManager();
    }

    public static boolean isManager() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .parallelStream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("Administrator") || authority.equals("Manager"));
    }
}
