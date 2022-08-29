package com.auth.jwt.service;

import com.auth.jwt.dto.response.CartResponse;
import com.auth.jwt.dto.response.CartResponseForUser;
import com.auth.jwt.model.Cart;
import com.auth.jwt.repository.BooksRepo;
import com.auth.jwt.repository.CartRepo;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final BooksRepo booksRepo;

    public Cart addProductToCart(Long bookId, int quantity, String note, AppUser user){
        var book = booksRepo.findById(bookId)
                .orElseThrow(RuntimeException::new);
        var cart = new Cart();
        cart.setQuantity(quantity);
        cart.setTotalPrice(cart.getQuantity() * book.getPrice());
        cart.setPrice(book.getPrice());
        cart.setNote(note);
        cart.setBook(book);
        cart.setUser(user);
        return cartRepo.save(cart);
    }

    public CartResponseForUser joinCartAndUser(AppUser user){
        List<Cart> carts = cartRepo.findCartByUser(user);
        List<CartResponse> cartResponses = new ArrayList<>();
        double totalCost = 0;
        for(Cart cart: carts){
            var cartResponse = new CartResponse(cart);
            totalCost += cart.getQuantity() * cart.getBook().getPrice();
            cartResponses.add(cartResponse);
        }
        var responseForUser = new CartResponseForUser();
        responseForUser.setTotalCost(totalCost);
        responseForUser.setCartResponses(cartResponses);
        return responseForUser;
    }

    public void deleteCart(Long cartId){
        cartRepo.deleteById(cartId);
    }

    @Transactional
    public Cart editCart(Long cartId, int quantity, String note, Long userId) {
        Optional<Cart> cart = cartRepo.findById(cartId);
        var user = userRepo.findById(userId).orElseThrow(
                RuntimeException::new);
        if (cart.isEmpty()) {
            throw new RuntimeException("Cart Not Found!");
        }
        Cart newCart = cart.get();
        if (newCart.getUser() != user) {
            throw new RuntimeException("User not same!!");
        }
        if(!noteNotNullOrEmpty(note)) {
            newCart.setNote(newCart.getNote());
        } else {
            newCart.setNote(note);
        }
        newCart.setQuantity(quantity);
        newCart.setTotalPrice(newCart.getPrice() * quantity);
        return cartRepo.save(newCart);
    }

    private static boolean noteNotNullOrEmpty(String note){
        return !note.isEmpty();
    }


}
