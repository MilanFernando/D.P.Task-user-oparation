package com.usercrud.demo.validation;

import com.usercrud.demo.anotation.ValidateNic;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NicValidator implements ConstraintValidator<ValidateNic, String> {


    @Override
    public void initialize(ValidateNic constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        String nicRegex = "^[0-9]{9}[Vv]$|^[0-9]{10}$" ;
        Pattern nicPattern = Pattern.compile(nicRegex);
        Matcher nicMatcher = nicPattern.matcher(value);

        return nicMatcher.matches();
    }

}
