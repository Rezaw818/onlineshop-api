package com.example.dataaccess.repository.payment;

import com.example.dataaccess.entity.payment.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    Optional<Transaction> findFirstByAuthorityIgnoreCase(String authority);
}
