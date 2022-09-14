package com.auth.jwt.validator.payment;

import com.auth.jwt.dto.request.OrderRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PaymentConstraint implements ConstraintValidator<PaymentMethodAnnotation, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        var orderRequest = (OrderRequestDto) o;
        return orderRequest.getPaymentMethod().equalsIgnoreCase("OVO")
                && orderRequest.getPaymentMethod().equalsIgnoreCase("BRI")
                && orderRequest.getPaymentMethod().equalsIgnoreCase("BCA");
    }
}
