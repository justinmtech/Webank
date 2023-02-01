package com.justinmtech.webank.service;

import com.justinmtech.webank.exceptions.user.UserAlreadyExistsError;
import com.justinmtech.webank.exceptions.user.UserNotFoundError;
import com.justinmtech.webank.model.Transaction;
import com.justinmtech.webank.model.User;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionServiceTest {

    private static Transaction transaction;
    private static User sender;
    private static User receiver;
    private static BigDecimal amount;

    @Autowired
    private UserService userService;

    @Autowired TransactionService transactionService;

    @BeforeAll
    static void setup() {
        sender = new User("testSender@gmail.com", "pass");
        sender.setBalance(BigDecimal.valueOf(1_000.00).setScale(2, RoundingMode.UNNECESSARY));
        receiver = new User("testReceiver@gmail.com", "pass");
        receiver.setBalance(BigDecimal.valueOf(0));
        amount = BigDecimal.valueOf(850.00).setScale(2, RoundingMode.UNNECESSARY);
        transaction = new Transaction();
        transaction.setSender(sender.getUsername());
        transaction.setReceiver(receiver.getUsername());
        transaction.setAmount(amount);
    }

    @Test
    @Order(1)
    void saveUsersAndTransactions() throws UserAlreadyExistsError {
        userService.createUser(sender).join();
        userService.createUser(receiver).join();
    }

    @Test
    @Order(2)
    void handleTransaction() throws Exception {
        transactionService.handleTransaction(transaction);
    }

    @Test
    @Order(3)
    //TODO Test transactions with decimals
    void areNewBalancesCorrect() throws UserNotFoundError {
        System.out.println("Amount: " + amount);
        BigDecimal receiverNewBalance = userService.getUserBalance(receiver.getUsername());
        assertEquals(receiver.getBalance().add(amount).setScale(2, RoundingMode.UNNECESSARY), receiverNewBalance);
        BigDecimal senderNewBalance = userService.getUserBalance(sender.getUsername());
        assertEquals(sender.getBalance().subtract(amount).setScale(2, RoundingMode.UNNECESSARY), senderNewBalance);
    }

    @Test
    void getTransactionById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getTransactionsBySender() {
    }

    @Test
    void getTransactionsByReceiver() {
    }

    @Test
    void getAllTransactionsByUsername() {
    }
}