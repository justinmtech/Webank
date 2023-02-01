package com.justinmtech.webank.repository;

import com.justinmtech.webank.model.Transaction;
import com.justinmtech.webank.model.User;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionRepositoryTest {

    private static User sender;
    private static User receiver;
    private static Transaction transaction;
    private static BigDecimal amount;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void setup() {
        sender = new User("justin.mitchell09@gmail.com", "password");
        receiver = new User("contact@justinmtech.com", "password");
        transaction = new Transaction();
        amount = BigDecimal.valueOf(500.25);
        transaction.setSender(sender.getUsername());
        transaction.setReceiver(receiver.getUsername());
        transaction.setTime(new Date());
        transaction.setAmount(amount);
    }

    @Test
    @Order(1)
    void saveUsers() {
        userRepository.save(sender);
        assertTrue(userRepository.existsById(sender.getUsername()));
        userRepository.save(receiver);
        assertTrue(userRepository.existsById(receiver.getUsername()));
    }

    @Test
    @Order(2)
    void createTransaction() {
        repository.save(transaction);
        List<Transaction> transactionList = repository.findBySender(sender.getUsername());
        assertTrue(transactionList.size() > 0);
        List<Transaction> receiverTransactionList = repository.findByReceiver(receiver.getUsername());
        assertTrue(receiverTransactionList.size() > 0);
    }

    @Test
    @Order(3)
    void findByReceiver() {
        List<Transaction> transactions = repository.findByReceiver(receiver.getUsername());
        assertEquals(amount, transactions.get(0).getAmount());
        assertEquals(sender.getUsername(), transactions.get(0).getSender());
        assertEquals(receiver.getUsername(), transactions.get(0).getReceiver());
    }

    @Test
    @Order(4)
    void findBySender() {
        List<Transaction> transactions = repository.findBySender(sender.getUsername());
        assertEquals(amount, transactions.get(0).getAmount());
        assertEquals(sender.getUsername(), transactions.get(0).getSender());
        assertEquals(receiver.getUsername(), transactions.get(0).getReceiver());
    }
}