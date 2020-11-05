package tw.edu.ntub.imd.camping.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.config.util.ResponseUtils;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.LogRecordDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.LogRecord;
import tw.edu.ntub.imd.camping.util.http.RequestUtils;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;
import tw.edu.ntub.imd.camping.wrapper.RequestWrapper;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@SuppressWarnings("unused")
public class LogAspect {
    private final BuildProperties buildProperties;
    private final LogRecordDAO logRecordDAO;

    @Autowired
    public LogAspect(BuildProperties buildProperties, LogRecordDAO logRecordDAO) {
        this.buildProperties = buildProperties;
        this.logRecordDAO = logRecordDAO;
    }

    @Pointcut(
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
                    "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
                    "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
                    "@annotation(org.springframework.web.bind.annotation.PatchMapping) || " +
                    "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
                    "@annotation(org.springframework.web.bind.annotation.ExceptionHandler)" +
                    "execution(" +
                    "public " +
                    "org.springframework.http.ResponseEntity<String> " +
                    "*.*(..)" +
                    ")"
    )
    public void controllerPointcut() {

    }

    @Pointcut("execution(public void tw.edu.ntub.imd.camping.config.handler.CustomAuthenticationSuccessHandler.onAuthenticationSuccess(..))")
    public void loginSuccessPointcut() {

    }

    @Pointcut("execution(public void tw.edu.ntub.imd.camping.config.handler.CustomAuthenticationFailHandler.onAuthenticationFailure(..))")
    public void loginFailPointcut() {

    }

    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
    public void afterControllerReturn(JoinPoint joinPoint, ResponseEntity<String> result) {
        ObjectData resultData = new ObjectData(result.getBody());
        Boolean isSuccess = resultData.getBoolean("result");
        String errorCode = resultData.getString("errorCode");
        String message = resultData.getString("message");
        log(isSuccess, errorCode, message);
    }

    private void log(Boolean isSuccess, String errorCode, String message) {
        HttpServletRequest request = RequestUtils.getRequest();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        LogRecord record = new LogRecord();
        record.setServerVersion(buildProperties.getVersion());
        record.setIp(requestWrapper.getIpv4());
        record.setMethod(request.getMethod());
        record.setUrl(getFullUrl(request));
        record.setExecutor(SecurityUtils.getLoginUserAccount());
        record.setDevice(requestWrapper.getDevice());
        record.setDeviceType(requestWrapper.getDeviceType());
        record.setDeviceVersion(requestWrapper.getDeviceVersion());
        record.setSuccess(isSuccess);
        record.setErrorCode(errorCode);
        record.setMessage(message != null ? message : "");
        logRecordDAO.saveAndFlush(record);
    }

    private String getFullUrl(HttpServletRequest request) {
        StringBuffer requestURLWithoutQueryString = request.getRequestURL();
        StringBuilder requestURL = new StringBuilder(requestURLWithoutQueryString.toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    @AfterReturning(pointcut = "loginSuccessPointcut()")
    public void afterLoginSuccessReturn(JoinPoint joinPoint) {
        String errorCode = ResponseUtils.LOGIN_SUCCESS_ERROR_CODE;
        String message = ResponseUtils.LOGIN_SUCCESS_MESSAGE;
        log(true, errorCode, message);
    }

    @AfterReturning(pointcut = "loginFailPointcut()")
    public void afterLoginFailReturn(JoinPoint joinPoint) {
        String errorCode = ResponseUtils.LOGIN_FAIL_ERROR_CODE;
        AuthenticationException exception = getLoginFailException(joinPoint);
        log(true, errorCode, exception.getMessage());
    }

    private AuthenticationException getLoginFailException(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            if (parameterName.equals("exception")) {
                return (AuthenticationException) args[i];
            }
        }
        throw new IllegalArgumentException("查無此參數");
    }
}
