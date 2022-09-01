package com.auth.jwt.controller;

import com.auth.jwt.dto.request.CheckOutItemRequestDto;
import com.auth.jwt.dto.response.StripeResponseDto;
import com.auth.jwt.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping()
@RequiredArgsConstructor
public class StripeController {

    private final StripeService stripeService;

    @PostMapping("/item")
    @PreAuthorize("#userid == principal.id or hasRole('ROLE_ADMIN')")
    public void order(@RequestParam Long userid, @RequestBody List<CheckOutItemRequestDto> checkOutItemRequestDto) throws StripeException {
        Session session = stripeService.createSession(checkOutItemRequestDto);
        StripeResponseDto stripeResponseDto = new StripeResponseDto(session.getId());
    }
}
