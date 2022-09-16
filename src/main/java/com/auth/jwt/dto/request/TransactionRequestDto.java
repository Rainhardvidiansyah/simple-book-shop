package com.auth.jwt.dto.request;


import com.auth.jwt.validator.payment.PaymentMethodAnnotation;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
//ToDo: make one annotation to validate payment method here!
public class TransactionRequestDto {

    private String paymentMethod;

    private String orderNumber;

    private double totalPrice;

}
