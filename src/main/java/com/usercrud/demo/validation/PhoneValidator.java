package com.usercrud.demo.validation;

import com.usercrud.demo.anotation.ValidatePhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<ValidatePhone, String> {
    @Override
    public void initialize(ValidatePhone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        String phoneRegex = "^0\\d{9}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Matcher phoneMatcher = phonePattern.matcher(value);

        return phoneMatcher.matches();
    }
}
