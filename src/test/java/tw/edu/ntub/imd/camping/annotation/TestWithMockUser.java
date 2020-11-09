package tw.edu.ntub.imd.camping.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Test
@WithMockUser(username = "mock", password = "mock", authorities = "Administrator")
public @interface TestWithMockUser {
}
