package com.auth.jwt.service;


import com.auth.jwt.dto.request.CheckOutItemRequestDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String apiKey;

    @Value("${BASE-URL}")
    private String baseUrl;

    public Session createSession(List<CheckOutItemRequestDto> checkOutItemRequestDto) throws StripeException {
        String successPaymentUrl = baseUrl + "/payment/success";
        String failureUrl = baseUrl + "/payment/failed";
        Stripe.apiKey = apiKey;
        List<SessionCreateParams.LineItem> createParamsLineItem = new ArrayList<>();
        for(CheckOutItemRequestDto checkOutItemRequest : checkOutItemRequestDto){
            createParamsLineItem.add(createSessionLineItem(checkOutItemRequest));
        }
        SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureUrl)
                .setSuccessUrl(successPaymentUrl)
                .addAllLineItem(createParamsLineItem)
                .build();
        return Session.create(sessionCreateParams);
    }

    private SessionCreateParams.LineItem createSessionLineItem(CheckOutItemRequestDto checkOutItemRequest) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(checkOutItemRequest))
                .setQuantity(Long.parseLong(String.valueOf(checkOutItemRequest.getQuantity())))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CheckOutItemRequestDto checkOutItemRequest) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("idr")
                .setUnitAmount((long) checkOutItemRequest.getPrice())
                .setProductData(SessionCreateParams.LineItem
                        .PriceData
                        .ProductData.builder()
                        .setName(checkOutItemRequest.getProductName())
                        .build()).build();
    }


}
