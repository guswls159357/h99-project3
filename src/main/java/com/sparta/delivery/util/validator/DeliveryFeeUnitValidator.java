package com.sparta.delivery.util.validator;

import com.sparta.delivery.util.annotaion.DeliveryFeeUnit;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

@Component
public class DeliveryFeeUnitValidator implements ConstraintValidator<DeliveryFeeUnit,Integer> {

    @Override
    public void initialize(DeliveryFeeUnit constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        boolean isValueValid = value % 500 == 0 ? true : false;

        if (!isValueValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            MessageFormat.format("배달 요금 {0}: 500원 단위로 입력하셔야 합니다", value))
                    .addConstraintViolation();
        }
        return isValueValid;
    }
}
