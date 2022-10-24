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

@AllArgsConstructor
@Getter @Setter
public class AdminTransactionResponse {

    private long userid;
    private String userName;
    private Long transactionId;
    private String orderNumber;
    private Date orderDate;
    private boolean hasBeenPaid;
    List<String> allBooks = new ArrayList<>();

    public AdminTransactionResponse(){
        this.hasBeenPaid = false;
    }

    public static AdminTransactionResponse from(Transaction transaction){
        return new AdminTransactionResponse(transaction.getUser().getId(), transaction.getUser().getEmail(),
                transaction.getId(), transaction.getOrderNumber(), transaction.getOrder().getCreatedDate(), false,
                transaction.getOrder().getOrderItems().stream().map(item -> item.getBook().getTitle()).collect(Collectors.toList())
        );
    }
}
