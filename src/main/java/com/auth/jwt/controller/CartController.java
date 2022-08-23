package com.auth.jwt.controller;

import com.auth.jwt.dto.request.CartRequestDto;
import com.auth.jwt.dto.response.CartResponse;
import com.auth.jwt.model.Cart;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final HttpServletRequest servletRequest;
    private final UserRepo userRepo;


    @PostMapping("/add-book")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole ('ROLE_USER')")
    public ResponseEntity<?> addBookToCart(@RequestBody CartRequestDto cartDto){
        var email = servletRequest.getUserPrincipal().getName();

        var user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        var savedCart = cartService.addProductToCart(cartDto.getBookId(), cartDto.getQuantity(),
                cartDto.getNote(), user);

        log.info("Book id: {}", cartDto.getBookId());
        log.info("Note: {}", cartDto.getNote());
        log.info("User: {}", user);
        return new ResponseEntity<>(savedCart, HttpStatus.OK);
    }

    @GetMapping("/get-cart")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole ('ROLE_USER')")
    public ResponseEntity<?> userCart(){
        String email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        var cartList = cartService.joinCartAndUser(user);
        List<CartResponse> cartResponses = new ArrayList<>();
        for(Cart cart : cartList){
            cartResponses.add(CartResponse.From(cart));
        }
        log.info("User email: {}", user.getEmail());
        log.info("Cart contains: {}", cartList);
        return new ResponseEntity<>(cartResponses, HttpStatus.OK);
    }


}
