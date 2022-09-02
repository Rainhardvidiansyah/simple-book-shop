package com.auth.jwt.dto.request;

import com.auth.jwt.model.Order;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class EmailRequestOrderDto {

    private String order_id;
    private boolean ordered;
    private String to;
    private String from;

    public static EmailRequestOrderDto From(Order order, String to, String from){
        return new EmailRequestOrderDto(order.getId(), order.isOrdered(), to, from);
    }

}
