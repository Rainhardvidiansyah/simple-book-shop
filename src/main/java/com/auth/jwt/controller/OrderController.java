package com.auth.jwt.controller;

import com.auth.jwt.dto.request.CheckOutItemRequestDto;
import com.auth.jwt.dto.response.StripeResponseDto;
import com.auth.jwt.service.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.StripeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/item")
    @PreAuthorize("#userid == principal.id or hasRole('ROLE_ADMIN')")
    public void order(@RequestParam Long userid, @RequestBody List<CheckOutItemRequestDto> checkOutItemRequestDto) throws StripeException {
        Session session = orderService.createSession(checkOutItemRequestDto);
        StripeResponseDto stripeResponseDto = new StripeResponseDto(session.getId());
    }
}
