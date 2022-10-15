package com.auth.jwt.dto.response;


import com.auth.jwt.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class AdminTransactionResponse {

    private long userid;
    private String userName;
    private Long transactionId;
    private String orderNumber;
    private Date orderDate;
    List<String> allBooks = new ArrayList<>();

    public static AdminTransactionResponse from(Transaction transaction){
        return new AdminTransactionResponse(transaction.getUser().getId(), transaction.getUser().getEmail(),
                transaction.getId(), transaction.getOrderNumber(), transaction.getOrder().getCreatedDate(),
                transaction.getOrder().getOrderItems().stream().map(item -> item.getBook().getTitle()).collect(Collectors.toList())
        );
    }
}
