package com.justinmtech.webank.repository;

import com.justinmtech.webank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByReceiver(String receiver_username);
    List<Transaction> findBySender(String sender_username);
}
