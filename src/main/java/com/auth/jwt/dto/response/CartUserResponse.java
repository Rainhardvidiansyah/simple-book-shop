package com.auth.jwt.dto.response;

import com.auth.jwt.model.Cart;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class CartUserResponse {

    private Long userId;
    private Long cartId;
    private String bookTitle;
    private int quantity;
    private Double price;
    private String note;


    public static CartUserResponse from(Cart cart){
        return new CartUserResponse(cart.getUser().getId(), cart.getId(), cart.getBook().getTitle(),
                cart.getQuantity(), cart.getPrice(), cart.getNote());
    }



}
