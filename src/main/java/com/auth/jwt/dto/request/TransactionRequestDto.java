package com.auth.jwt.dto.request;


import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
//ToDo: make one annotation to validate payment method here!
public class TransactionRequestDto {

    private String paymentMethod;

    private String orderNumber;

    private double totalPrice;

    private String senderAccountNumber;

    private String senderBank;

}
