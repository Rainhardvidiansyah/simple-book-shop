package com.auth.jwt.dto.response;


import com.auth.jwt.model.Book;
import com.auth.jwt.model.Cart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@Getter @Setter @ToString
public class CartResponse {

    private String title;
    private int quantity;
    private String note;
    private Double totalPrice;
    @JsonIgnore
    private Book book;

    public CartResponse(Cart cart) {
        this.title = cart.getBook().getTitle();
        this.quantity = cart.getQuantity();
        this.note = cart.getNote();
        this.totalPrice = cart.getTotalPrice();
        this.book = cart.getBook();
    }
}
