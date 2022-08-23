package com.auth.jwt.dto.response;


import com.auth.jwt.model.Cart;
import lombok.*;

@NoArgsConstructor
@Getter @Setter @ToString
public class CartResponse {

    private String title;
    private int quantity;

    private String note;

    private String totalPrice;

    public CartResponse(String title, int quantity, String note) {
        this.title = title;
        this.quantity = quantity;
        this.note = note;
    }

    public static CartResponse From(Cart cart){
        return new CartResponse(cart.getBook().getTitle(), cart.getQuantity(), cart.getNote());
    }
}
