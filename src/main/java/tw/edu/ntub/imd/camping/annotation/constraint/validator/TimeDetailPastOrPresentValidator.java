package tw.edu.ntub.imd.camping.annotation.constraint.validator;

import tw.edu.ntub.birc.common.wrapper.date.TimeDetail;
import tw.edu.ntub.birc.common.wrapper.date.TimeWrapperImpl;
import tw.edu.ntub.imd.camping.annotation.constraint.Past;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeDetailPastOrPresentValidator implements ConstraintValidator<Past, TimeDetail> {
    @Override
    public void initialize(Past constraintAnnotation) {

    }

    @Override
    public boolean isValid(TimeDetail value, ConstraintValidatorContext context) {
        return value == null || value.isBeforeOrEquals(new TimeWrapperImpl());
    }
}
