package com.auth.jwt.repository;

import com.auth.jwt.model.Order;
import com.auth.jwt.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, String> {

    List<Order> findAllByUserOrderByCreatedDateDesc(AppUser user);
    Order findOrderByUser(AppUser user);


}
