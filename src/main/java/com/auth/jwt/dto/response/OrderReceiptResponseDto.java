package com.auth.jwt.dto.response;

import com.auth.jwt.model.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter @ToString
public class OrderReceiptResponseDto {

    private String order_id;
    @JsonProperty("")
    private List<String> book_collection = new ArrayList<>();
    private String user_email;
    private Double totalPrice;

    public OrderReceiptResponseDto(String order_id, List<String> book_collection,
                                   String user_email, Double totalPrice) {
        this.order_id = order_id;
        this.book_collection = book_collection;
        this.user_email = user_email;
        this.totalPrice = totalPrice;
    }

    public static OrderReceiptResponseDto From(Order order){
        return new OrderReceiptResponseDto(
                order.getId(),
                order.getOrderItems().stream().map(item -> item.getBook().getTitle()).collect(Collectors.toList()),
                order.getUser().getEmail(),
                order.getTotalPrice()
        );
    }
}
