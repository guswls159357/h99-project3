package com.sparta.delivery.util.annotaion;

import com.sparta.delivery.util.validator.MinOrderUnitValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinOrderUnitValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Valid100Unit {

    String message() default "100원 단위로 입력하셔야 합니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
