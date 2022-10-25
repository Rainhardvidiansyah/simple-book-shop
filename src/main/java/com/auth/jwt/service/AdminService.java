package com.auth.jwt.service;

import com.auth.jwt.model.History;
import com.auth.jwt.model.Order;
import com.auth.jwt.model.Transaction;
import com.auth.jwt.repository.OrderRepo;
import com.auth.jwt.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final OrderRepo orderRepo;
    private final TransactionRepo transactionRepo;
    private final HistoryService historyService;
    private final EmailService emailService;

    public void verifyOrder(String transactionNumber, boolean changeStatus){
        var order = findOrderNumber(transactionNumber);
        History history = null;
            order.setOrdered(changeStatus);
            if(order.isOrdered()){
                history = historyService.saveMyHistory(order, order.getUser());
            }
            emailService.sendConfirmationSuccess(order.getId(), order.getUser());
        orderRepo.save(order);
    }

    public List<Order> getAllOrder(){
        var order = orderRepo.findAllByOrderByCreatedDateAsc();
        return order;
    }

    public List<Transaction> getAllTransaction(){
        return transactionRepo.findAllByOrderByDateAsc();
    }

    public Order findOrderNumber(String orderId){
        return orderRepo.findOrderById(orderId);
    }

    public List<Order> findOrderedTrue(){
        return orderRepo.findAllOrderedTrue();
    }
    public List<Order> findOrderedFalse(){
        return orderRepo.findAllOrderedFalse();
    }
}
