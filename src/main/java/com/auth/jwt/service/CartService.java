package com.auth.jwt.service;

import com.auth.jwt.model.Cart;
import com.auth.jwt.repository.BooksRepo;
import com.auth.jwt.repository.CartRepo;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;
    private final BooksRepo booksRepo;

    public Cart addProductToCart(Long bookId, int quantity, String note, AppUser user){
        var book = booksRepo.findById(bookId)
                .orElseThrow(RuntimeException::new);
        var cart = new Cart();
        cart.setQuantity(quantity);
//        cart.setTotalPrice(PLEASE CHANGE THIS VALUE TO DOUBLE OR ANYTHING ELSE THAT CORRESPONDS TO CURRENCY);
        cart.setPrice(book.getPrice());
        cart.setNote(note);
        cart.setBook(book);
        cart.setUser(user);
        return cartRepo.save(cart);
    }


    public List<Cart> joinCartAndUser(AppUser user){
        return cartRepo.findCartByUser(user);
    }
}
