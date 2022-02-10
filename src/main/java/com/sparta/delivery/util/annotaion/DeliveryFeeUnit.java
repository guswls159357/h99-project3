package com.sparta.delivery.util.annotaion;

import com.sparta.delivery.util.validator.DeliveryFeeUnitValidator;
import com.sparta.delivery.util.validator.MinOrderUnitValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DeliveryFeeUnitValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)public @interface DeliveryFeeUnit {

    String message() default "500원 단위로 입력하셔야 합니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
