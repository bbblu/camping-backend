package tw.edu.ntub.imd.camping.annotation.constraint;

import tw.edu.ntub.imd.camping.annotation.constraint.validator.DateDetailPastOrPresentValidator;
import tw.edu.ntub.imd.camping.annotation.constraint.validator.TimeDetailPastOrPresentValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {DateDetailPastOrPresentValidator.class, TimeDetailPastOrPresentValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PastOrPresent {
    String message() default "應為過去或現在時間";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
