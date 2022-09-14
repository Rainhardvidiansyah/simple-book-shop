package com.auth.jwt.validator.payment;

import com.auth.jwt.dto.request.OrderRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PaymentConstraint implements ConstraintValidator<PaymentMethodAnnotation, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        var orderRequest = (OrderRequestDto) o;

        if(orderRequest.getPaymentMethod().equalsIgnoreCase("ovo")){
            return true;
        } else if(orderRequest.getPaymentMethod().equalsIgnoreCase("bri")) {
            return true;
        } else if(orderRequest.getPaymentMethod().equalsIgnoreCase("bca")) {
            return true;
        }else {
            return false;
        }
    }
}
