package com.usercrud.demo.anotation;

import com.usercrud.demo.validation.PhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PhoneValidator.class})
public @interface ValidatePhone {
    String message() default "Phone number is not Valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
