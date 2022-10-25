package com.auth.jwt.service;

import com.auth.jwt.model.Transaction;

import com.auth.jwt.repository.TransactionRepo;
import com.auth.jwt.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final OrderService orderService;
    private final UserRepo userRepo;

    public Transaction transaction(Long userId, String orderNumber, double totalPrice, String paymentMethod, String senderAccountNumber, String senderBank){
        var user = userRepo.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found!"));

        var order = orderService.findOrderById(orderNumber);
        if(user.getId() != order.getUser().getId()){
            throw new RuntimeException("User is different");
        }
        var transaction = new Transaction();
        try {
            if(totalPrice == order.getTotalPrice()){
                transaction.setPaymentMethod(paymentMethod);
                transaction.setOrderNumber(order.getId());
                transaction.setTotalPrice(totalPrice);
                transaction.setSenderAccountNumber(senderAccountNumber);
                transaction.setSenderBank(senderBank);
                transaction.setDate(new Date());
                transaction.setOrder(order);
                transaction.setUser(order.getUser());
                order.setTransaction(transaction);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return transactionRepo.save(transaction);
    }


}
