package tw.edu.ntub.imd.camping.annotation.constraint;

import tw.edu.ntub.imd.camping.annotation.constraint.validator.DateDetailPastValidator;
import tw.edu.ntub.imd.camping.annotation.constraint.validator.TimeDetailPastValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {DateDetailPastValidator.class, TimeDetailPastValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Past {
    String message() default "應為過去時間";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
