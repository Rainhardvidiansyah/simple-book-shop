package com.auth.jwt.service;


import com.auth.jwt.model.History;
import com.auth.jwt.repository.HistoryRepo;
import com.auth.jwt.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepo historyRepo;
    private final OrderService orderService;
    private final UserRepo userRepo;

    public History saveMyHistory(Long userid){
         var order = orderService.getOrder(userid);
         var history = new History();
         if(order.isOrdered() == true){
             history.setUser(order.getUser());
             history.setOrder(order);
         }
        return historyRepo.save(history);
    }



}
