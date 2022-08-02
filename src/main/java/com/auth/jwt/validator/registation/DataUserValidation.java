package com.auth.jwt.validator.registation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DataUserValidation implements ConstraintValidator<DataUserConstraint, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext ctx) {
        return !s.isEmpty();
    }
}
