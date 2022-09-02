package com.auth.jwt.controller;

import com.auth.jwt.dto.request.OrderRequestDto;
import com.auth.jwt.dto.utils.ErrorUtils;
import com.auth.jwt.model.Order;
import com.auth.jwt.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("#userid == principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> order(@RequestParam Long userid, @Valid @RequestBody OrderRequestDto orderRequestDto, Errors errors){
        if(errors.hasErrors()){
            return new ResponseEntity<>(ErrorUtils.err(errors), HttpStatus.BAD_REQUEST);
        }
        if(isUserIdValid(userid)){
            return new ResponseEntity<>("Data not valid", HttpStatus.BAD_REQUEST);
        }
        Order order = orderService.makeAnOrder(userid, orderRequestDto.getPaymentMethod());
        return new ResponseEntity<>(headerResponses(order.getId(), String.valueOf(order.getTotalPrice()) ,order.getPayment_method()),
                HttpStatus.OK);
    }

    @GetMapping("/user")
    @ResponseBody
    @PreAuthorize("#userid == principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getOrder(@RequestParam Long userid){
        var order = orderService.getOrder(userid);
        return new ResponseEntity<>(headerResponses(order.getId(), String.valueOf(order.getTotalPrice()),
                order.getPayment_method()), HttpStatus.OK);
    }

    private ResponseEntity<?> headerResponses(String number_order, String totalCost, String payment_method){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Order number:", number_order);
        headers.add("Total Cost:", totalCost);
        headers.add("Payment Method: ", payment_method);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
    private static boolean isUserIdValid(Long userId){
        return !userId.equals("") && userId==null;
    }
}
