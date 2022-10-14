package com.auth.jwt.service;

import com.auth.jwt.model.Order;
import com.auth.jwt.repository.OrderRepo;
import com.auth.jwt.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepo userRepo;
    private final OrderRepo orderRepo;

    public void makeOrderTrue(String email){
        var user = userRepo.findAppUserByEmail(email);

    }

    public List<Order> getAllOrder(){
        var order = orderRepo.findAll();
        return order;
    }
}
