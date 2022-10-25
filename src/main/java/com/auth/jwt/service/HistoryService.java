package com.auth.jwt.service;


import com.auth.jwt.model.History;
import com.auth.jwt.model.Order;
import com.auth.jwt.repository.HistoryRepo;
import com.auth.jwt.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepo historyRepo;

    public History saveMyHistory(Order order, AppUser user){
        var history = new History();
        history.setUser(order.getUser());
        history.setOrder(order);
        return historyRepo.save(history);
    }

    public List<History> getMyHistory(AppUser user){
        return historyRepo.findHistoryByUser(user);
    }



}
