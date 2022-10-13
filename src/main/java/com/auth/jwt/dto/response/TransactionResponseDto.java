package com.auth.jwt.dto.response;


import com.auth.jwt.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter @ToString
public class TransactionResponseDto {

    private Long transactionId;

    private String paymentMethod;

    private String orderNumber;

    private double totalPrice;

    private String senderAccountNumber;

    private String senderBank;

    public static TransactionResponseDto from(Transaction transaction){
        return new TransactionResponseDto(
                transaction.getId(), transaction.getPaymentMethod(),
                transaction.getOrderNumber(), transaction.getTotalPrice(),
                transaction.getSenderAccountNumber(), transaction.getSenderBank());
    }


}
