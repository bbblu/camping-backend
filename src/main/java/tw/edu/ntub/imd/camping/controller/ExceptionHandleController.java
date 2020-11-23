package tw.edu.ntub.imd.camping.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import tw.edu.ntub.birc.common.exception.ProjectException;
import tw.edu.ntub.birc.common.exception.UnknownException;
import tw.edu.ntub.birc.common.exception.date.ParseDateException;
import tw.edu.ntub.birc.common.util.ClassUtils;
import tw.edu.ntub.imd.camping.exception.ConvertPropertyException;
import tw.edu.ntub.imd.camping.exception.MethodNotSupportedException;
import tw.edu.ntub.imd.camping.exception.NullRequestBodyException;
import tw.edu.ntub.imd.camping.exception.PermissionDeniedException;
import tw.edu.ntub.imd.camping.exception.file.FileNotExistException;
import tw.edu.ntub.imd.camping.exception.file.UploadFileTooLargeException;
import tw.edu.ntub.imd.camping.exception.form.InvalidFormDateFormatException;
import tw.edu.ntub.imd.camping.exception.form.InvalidFormNumberFormatException;
import tw.edu.ntub.imd.camping.exception.form.InvalidRequestFormatException;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@Log4j2
@ControllerAdvice
public class ExceptionHandleController {
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<String> handleProjectException(ProjectException e) {
        return ResponseEntityBuilder.error(e).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidFormatException(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) e.getCause();
            List<JsonMappingException.Reference> referenceList = invalidFormatException.getPath();
            String message = "";
            if (referenceList.size() > 0) {
                JsonMappingException.Reference reference = referenceList.get(0);
                Object from = reference.getFrom();
                String fieldName = reference.getFieldName();
                Class<?> fromClass = from.getClass();
                Field declaredField = null;
                while (declaredField == null) {
                    try {
                        declaredField = fromClass.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException ignored) {
                        fromClass = fromClass.getSuperclass();
                    }
                }
                String description;
                if (declaredField.isAnnotationPresent(Schema.class)) {
                    Schema schema = declaredField.getAnnotation(Schema.class);
                    description = schema.description();
                } else {
                    description = declaredField.getName();
                }
                if (ClassUtils.isCanCast(invalidFormatException.getTargetType(), Number.class)) {
                    message = description + " - \"" + invalidFormatException.getValue() + "\"輸入的文字中包含非數字文字";
                } else {
                    throw new UnknownException(e);
                }
            }
            return ResponseEntityBuilder.error(new InvalidRequestFormatException(message)).build();
        } else if (e.getRootCause() instanceof ParseDateException) {
            ParseDateException rootCause = (ParseDateException) e.getRootCause();
            return ResponseEntityBuilder.error(new InvalidFormDateFormatException(rootCause)).build();
        } else if (e.getRootCause() instanceof NumberFormatException) {
            NumberFormatException rootCause = (NumberFormatException) e.getRootCause();
            return ResponseEntityBuilder.error(new InvalidFormNumberFormatException(rootCause)).build();
        } else {
            return ResponseEntityBuilder.error(new NullRequestBodyException(e)).build();
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException e) throws IOException {
        log.error("沒有權限", e);
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith("application/json")) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(ResponseEntityBuilder.error(new PermissionDeniedException()).buildJSONString());
        } else {
            response.sendRedirect(request.getContextPath() + "/error403");
        }
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotExistException.class)
    public void handleFileNotExistException(FileNotExistException e) {
        log.error("找不到檔案", e);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResponseEntityBuilder.error(new UploadFileTooLargeException(e)).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(
            HttpServletRequest request,
            HttpRequestMethodNotSupportedException e
    ) {
        return ResponseEntityBuilder.error(new MethodNotSupportedException(
                request.getRequestURL().toString(),
                request.getMethod(),
                e
        )).build();
    }

    @ExceptionHandler(InvalidPropertyException.class)
    public ResponseEntity<String> handleInvalidPropertyException(InvalidPropertyException e) {
        return ResponseEntityBuilder.error(new ConvertPropertyException(e)).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnknownException(Exception e) {
        return ResponseEntityBuilder.error(new UnknownException(e)).build();
    }
}
