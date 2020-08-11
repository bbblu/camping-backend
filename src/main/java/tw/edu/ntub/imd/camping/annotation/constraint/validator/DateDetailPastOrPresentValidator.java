package tw.edu.ntub.imd.camping.annotation.constraint.validator;

import tw.edu.ntub.birc.common.wrapper.date.DateDetail;
import tw.edu.ntub.birc.common.wrapper.date.DateWrapperImpl;
import tw.edu.ntub.imd.camping.annotation.constraint.Past;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateDetailPastOrPresentValidator implements ConstraintValidator<Past, DateDetail> {
    @Override
    public void initialize(Past constraintAnnotation) {

    }

    @Override
    public boolean isValid(DateDetail value, ConstraintValidatorContext context) {
        return value == null || value.isBeforeOrEquals(new DateWrapperImpl());
    }
}
