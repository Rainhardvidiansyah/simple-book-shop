package com.auth.jwt.repository;

import com.auth.jwt.model.History;
import com.auth.jwt.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepo extends JpaRepository<History, Long> {

    List<History> findHistoryByUser(AppUser user);
}
