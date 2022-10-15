package com.auth.jwt.dto.response;

import com.auth.jwt.model.Order;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class AdminOrderResponse {

    private long userid;
    private String userName;
    private String orderNumber;
    private Date orderDate;
    private List<String> allBooks = new ArrayList<>();

    public static AdminOrderResponse from(Order order){
    return new AdminOrderResponse(order.getUser().getId(), order.getUser().getEmail(),
            order.getId(), order.getCreatedDate(),
            order.getOrderItems().stream().map(item -> item.getBook().getTitle()).collect(Collectors.toList()));
    }
}
