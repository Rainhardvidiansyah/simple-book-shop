package com.auth.jwt.service;

import com.auth.jwt.model.OrderItem;
import com.auth.jwt.repository.OrderItemRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService {

    private final OrderItemRepo orderItemRepo;

    public OrderItem addOrderedProducts(OrderItem orderItem) {
        return orderItemRepo.save(orderItem);
    }

}
