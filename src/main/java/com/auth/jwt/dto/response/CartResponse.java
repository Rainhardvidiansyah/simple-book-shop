package com.auth.jwt.dto.response;


import com.auth.jwt.model.Cart;
import lombok.*;

@NoArgsConstructor
@Getter @Setter @ToString
public class CartResponse {

    private String title;
    private int quantity;
    private String note;
    private Double totalPrice;

    public CartResponse(Cart cart) {
        this.title = cart.getBook().getTitle();
        this.quantity = cart.getQuantity();
        this.note = cart.getNote();
        this.totalPrice = cart.getTotalPrice();
    }
}
