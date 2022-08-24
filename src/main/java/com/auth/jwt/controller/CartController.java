package com.auth.jwt.controller;

import com.auth.jwt.dto.request.CartRequestDto;
import com.auth.jwt.dto.request.EditCartRequestDto;
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

import javax.servlet.http.HttpServletRequest;
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

    private static boolean isNotNull(List<CartResponse> cartResponses){
        return !cartResponses.isEmpty();
    }

    @GetMapping("/get-cart")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole ('ROLE_USER')")
    public ResponseEntity<?> userCart(){
        String email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        var cartResponseForUser = cartService.joinCartAndUser(user);
        if(!isNotNull(cartResponseForUser.getCartResponses())){
            return new ResponseEntity<>("You haven't added the product to your shopping cart yet",
                    HttpStatus.BAD_REQUEST);
        }
        log.info("User email: {}", user.getEmail());
        log.info("Cart contains: {}", cartResponseForUser);
        return new ResponseEntity<>(cartResponseForUser, HttpStatus.OK);
    }

    @PutMapping("/update")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole ('ROLE_USER')")
    public ResponseEntity<?> editCart(@RequestParam Long cart_Id, @RequestBody EditCartRequestDto editDto){
        var email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        if(editDto.getQuantity() == 0){
            return new ResponseEntity<>("Cart cannot be empty!", HttpStatus.BAD_REQUEST);
        }
        Cart savedCart = cartService.editCart(cart_Id, editDto.getQuantity(), editDto.getNote(), user);
        var response = new CartResponse(savedCart);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
