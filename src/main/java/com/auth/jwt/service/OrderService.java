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

    public Order makeAnOrder(Long userId){
        var user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException());

        CartResponseForUser cartResponseForUser = cartService.joinCartAndUser(user);
        List<CartResponse> cartResponses = cartResponseForUser.getCartResponses();

        var order = new Order();
        order.setCreatedDate(new Date());
        //order.setSessionId(sessionId);
        order.setUser(user);
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


    /*
     public void placeOrder(User user, String sessionId) {
        // first let get cart items for the user
        CartDto cartDto = cartService.listCartItems(user);

        List<CartItemDto> cartItemDtoList = cartDto.getcartItems();

        // create the order and save it
        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setSessionId(sessionId);
        newOrder.setUser(user);
        newOrder.setTotalPrice(cartDto.getTotalCost());
        orderRepository.save(newOrder);

        for (CartItemDto cartItemDto : cartItemDtoList) {
            // create orderItem and save each one
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(cartItemDto.getProduct().getPrice());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);
            // add to order item list
            orderItemsRepository.save(orderItem);
        }
        //
        cartService.deleteUserCartItems(user);
    }

    public List<Order> listOrders(User user) {
        return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
    }


    public Order getOrder(Integer orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        throw new OrderNotFoundException("Order not found");
    }
     */

}
