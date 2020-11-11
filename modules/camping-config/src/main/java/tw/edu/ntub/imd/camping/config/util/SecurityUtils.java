package tw.edu.ntub.imd.camping.config.util;

import org.springframework.security.core.context.SecurityContextHolder;
import tw.edu.ntub.birc.common.util.StringUtils;

public final class SecurityUtils {
    private SecurityUtils() {

    }

    public static String getLoginUserAccount() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean isLogin() {
        return StringUtils.isNotEquals(getLoginUserAccount(), "anonymousUser");
    }
}
