package com.auth.jwt.repository;

import com.auth.jwt.model.Order;
import com.auth.jwt.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepo extends JpaRepository<Order, String> {

    List<Order> findAllByUserOrderByCreatedDateDesc(AppUser user);
    List<Order> findAllByOrderByCreatedDateAsc();
    Order findOrderByUser(AppUser user);
    Order findOrderById(String orderNumber);
    @Query("select o from Order o where o.ordered = false")
    List<Order> findAllOrderedFalse();
    @Query("select o from Order o where o.ordered = true")
    List<Order> findAllOrderedTrue();


}
