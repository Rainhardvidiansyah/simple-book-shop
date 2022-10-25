package com.auth.jwt.dto.response;

import com.auth.jwt.model.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class OrderReceiptResponseDto {

    private Long userId;
    private String order_id;
    private List<String> book_collection = new ArrayList<>();
    private String user_email;
    private boolean hasBeenPaid;
    private Double totalPrice;


    public static OrderReceiptResponseDto From(Order order){
        return new OrderReceiptResponseDto(
                order.getUser().getId(),
                order.getId(),
                order.getOrderItems().stream().map(item -> item.getBook().getTitle()).collect(Collectors.toList()),
                order.getUser().getEmail(),
                order.isOrdered(),
                order.getTotalPrice()
        );
    }
}
