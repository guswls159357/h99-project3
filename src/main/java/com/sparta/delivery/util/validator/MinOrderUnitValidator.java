package com.sparta.delivery.util.validator;

import com.sparta.delivery.util.annotaion.Valid100Unit;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

@Component
public class MinOrderUnitValidator implements ConstraintValidator<Valid100Unit, Integer> {

    @Override
    public void initialize(Valid100Unit constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        boolean isValueValid = value % 100 == 0 ? true : false;

        if (!isValueValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            MessageFormat.format("최소 주문 금액 {0}: 100원 단위로 입력하셔야 합니다", value))
                    .addConstraintViolation();
        }
        return isValueValid;
    }
}
//
