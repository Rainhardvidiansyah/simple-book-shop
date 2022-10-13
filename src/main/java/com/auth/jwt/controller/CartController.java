package com.auth.jwt.controller;

import com.auth.jwt.dto.request.CartRequestDto;
import com.auth.jwt.dto.request.EditCartRequestDto;
import com.auth.jwt.dto.response.CartResponse;
import com.auth.jwt.dto.response.ResponseMessage;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final HttpServletRequest servletRequest;
    private final UserRepo userRepo;


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole ('ROLE_USER')")
    public ResponseEntity<?> addBookToCart(@RequestBody CartRequestDto cartDto){
        if (cartDto.getQuantity() < 1){
            return new ResponseEntity<>(generateFailedResponse("POST", List.of("Cart cannot be empty!")), HttpStatus.BAD_REQUEST);
        }
        var email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        var savedCart = cartService.addProductToCart(cartDto.getBookId(), cartDto.getQuantity(),
                cartDto.getNote(), user);
        log.info("Book id: {}", cartDto.getBookId());
        log.info("Note: {}", cartDto.getNote());
        log.info("User: {}", user);
        return new ResponseEntity<>(generateSuccessResponse("POST", savedCart), HttpStatus.OK);
    }

    private static boolean isNotNull(List<CartResponse> cartResponses){
        return !cartResponses.isEmpty();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole ('ROLE_USER')")
    public ResponseEntity<?> userCart(){
        String email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        var cartResponseForUser = cartService.joinCartAndUser(user);
        if(!isNotNull(cartResponseForUser.getCartResponses())){
            return new ResponseEntity<>(generateFailedResponse("GET", List.of("You haven't added the product to your shopping cart yet")),
                    HttpStatus.BAD_REQUEST);
        }
        log.info("User email: {}", user.getEmail());
        log.info("Cart contains: {}", cartResponseForUser);
        return new ResponseEntity<>(generateSuccessResponse("GET", cartResponseForUser), HttpStatus.OK);
    }

    @DeleteMapping("/{cart_id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole ('ROLE_USER')")
    public ResponseEntity<?> deleteCart(@PathVariable("cart_id") Long id){
        cartService.deleteCart(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("DELETED", Boolean.TRUE);
        return new ResponseEntity<>(generateSuccessResponse("DELETE", response), HttpStatus.OK);
    }

    @PutMapping("/update")
    @ResponseBody
    @PreAuthorize("#userid == principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> editCart(@RequestParam(required = true) Long userid, @RequestParam(required = true) Long cart_id,
                                      @RequestBody EditCartRequestDto editCartDto){
        var email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        if(editCartDto.getQuantity() == 0){
            return new ResponseEntity<>(generateFailedResponse("PUT", List.of("Cart cannot be empty!")), HttpStatus.BAD_REQUEST);
        }
        Cart savedCart = cartService.editCart(cart_id, editCartDto.getQuantity(), editCartDto.getNote(), userid);
        var response = new CartResponse(savedCart);
        return new ResponseEntity<>(generateSuccessResponse("PUT", response), HttpStatus.OK);
    }

    private ResponseMessage<Object> generateSuccessResponse(String method, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(200);
        responseMessage.setMethod(method);
        responseMessage.setMessage(List.of("Success"));
        responseMessage.setData(object);
        return responseMessage;
    }

    private ResponseMessage<Object> generateFailedResponse(String method, List<String> message){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(400);
        responseMessage.setMethod(method);
        responseMessage.setMessage(message);
        responseMessage.setData(null);
        return responseMessage;
    }


}
