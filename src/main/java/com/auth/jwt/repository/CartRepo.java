package com.auth.jwt.repository;

import com.auth.jwt.model.Cart;
import com.auth.jwt.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Long> {

    List<Cart> findCartByUser(AppUser user);

    void deleteCartByUser(AppUser user);




}
