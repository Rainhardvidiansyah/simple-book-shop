package com.auth.jwt.repository;

import com.auth.jwt.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findTransactionByPaymentMethod(String payment_method);
    List<Transaction> findAllByOrderByDateAsc();
    Optional<Transaction> findTransactionByOrderNumber(String orderNumber);
}
