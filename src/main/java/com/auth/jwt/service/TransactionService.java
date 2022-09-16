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

    public Transaction transaction(Long userId, String orderNumber, double totalPrice, String paymentMethod){
        var user = userRepo.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found!"));
        var order = orderService.findOrderId(orderNumber);
        var transaction = new Transaction();
        try {
            if(null != order){
                if(transaction.getTotalPrice() == order.getTotalPrice()){
                    transaction.setPaymentMethod(paymentMethod);
                    transaction.setOrderNumber(order.getId());
                    transaction.setDate(new Date());
                    transaction.setOrder(order);
                    transaction.setUser(order.getUser());
                    order.setTransaction(transaction);
                }
            }else{
                throw new RuntimeException("Error is happening");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return transactionRepo.save(transaction);
    }


}
