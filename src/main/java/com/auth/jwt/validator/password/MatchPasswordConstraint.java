package com.auth.jwt.validator.password;

import com.auth.jwt.dto.request.RegistrationRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchPasswordConstraint implements ConstraintValidator<MatchPasswordAnnotation, Object>{


    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        var registrationRequest = (RegistrationRequest) o;
        return registrationRequest.getPassword().equals(registrationRequest.getMatchPassword());
    }
}
