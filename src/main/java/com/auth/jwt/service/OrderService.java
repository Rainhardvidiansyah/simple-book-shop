package com.auth.jwt.service;

import com.auth.jwt.dto.request.CartRequestDto;
import com.auth.jwt.dto.response.CartResponse;
import com.auth.jwt.dto.response.CartResponseForUser;
import com.auth.jwt.model.Cart;
import com.auth.jwt.model.Order;
import com.auth.jwt.model.OrderItem;
import com.auth.jwt.repository.CartRepo;
import com.auth.jwt.repository.OrderRepo;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderItemService orderItemService;
    private final CartService cartService;
    private final UserRepo userRepo;

    public Order makeAnOrder(Long userId, String paymentMethod){
        var user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException());

        CartResponseForUser cartResponseForUser = cartService.joinCartAndUser(user);
        List<CartResponse> cartResponses = cartResponseForUser.getCartResponses();

        var order = new Order();
        order.setCreatedDate(new Date());
        //order.setSessionId(sessionId);
        order.setPayment_method(paymentMethod);
        order.setUser(user);
        if(user != order.getUser()){
            throw new RuntimeException("This is not your order!");
        }
        order.setTotalPrice(cartResponseForUser.getTotalCost());

        List<OrderItem> listOfOrderItem = new ArrayList<>();
        order.setOrderItems(listOfOrderItem);

        for(CartResponse cartResponse : cartResponses){
            var orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(cartResponseForUser.getTotalCost());
            orderItem.setBook(cartResponse.getBook());
            orderItem.setQuantity(cartResponse.getQuantity());

            orderItem.setOrder(order);
            orderItemService.addOrderedProducts(orderItem);
        }
        return orderRepo.save(order);
    }


    public List<Order> listOrders(AppUser user) {
        return orderRepo.findAllByUserOrderByCreatedDateDesc(user);
    }


    /*
    public Order getOrder(Integer orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        throw new OrderNotFoundException("Order not found");
    }
     */

}
