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

    public CartResponse(String title, int quantity, String note, Double totalPrice) {
        this.title = title;
        this.quantity = quantity;
        this.note = note;
        this.totalPrice = totalPrice;
    }

    public static CartResponse From(Cart cart){
        return new CartResponse(cart.getBook().getTitle(), cart.getQuantity(),
                cart.getNote(), cart.getTotalPrice());
    }
}
