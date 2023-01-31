package com.justinmtech.webank.model;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionTest {
    private static Transaction transaction;
    private static String sender;
    private static String receiver;
    private static BigDecimal amount;

    @BeforeAll
    static void setup() {
        sender = "justin.mitchell@gmail.com";
        receiver = "bob.mitchell@gmail.com";
        amount = BigDecimal.valueOf(56);
        transaction = new Transaction(sender, receiver, amount);
    }

    @Test
    @Order(1)
    void testConstructor() {
        sender = "justin.mitchell@gmail.com";
        receiver = "bob.mitchell@gmail.com";
        transaction = new Transaction(sender, receiver, amount);
        assertEquals(sender, transaction.getSender());
        assertEquals(receiver, transaction.getReceiver());
        assertEquals(amount, transaction.getAmount());
        assertEquals("justin.mitchell@gmail.com", transaction.getSender());
        assertEquals("bob.mitchell@gmail.com", transaction.getReceiver());
        assertNotNull(transaction.getTime());
    }

    @Test
    @Order(2)
    void getSender() {
        transaction.setSender(sender);
        assertEquals(sender, transaction.getSender());
    }

    @Test
    @Order(3)
    void setSender() {
        String sender = "testaccount@gmail.com";
        transaction.setSender(sender);
        assertEquals(sender, transaction.getSender());
    }

    @Test
    @Order(4)
    void getReceiver() {
        transaction.setReceiver(receiver);
        assertEquals(receiver, transaction.getReceiver());
    }

    @Test
    @Order(5)
    void setReceiver() {
        String receiver = "testaccount2@gmail.com";
        transaction.setReceiver(receiver);
        assertEquals(receiver, transaction.getReceiver());
    }

    @Test
    @Order(6)
    void getAmount() {
        transaction.setAmount(amount);
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    @Order(7)
    void setAmount() {
        BigDecimal amountTest = BigDecimal.valueOf(101010106);
        transaction.setAmount(amountTest);
        assertEquals(amountTest, transaction.getAmount());
    }

    @Test
    @Order(8)
    void getTime() {
        assertNotNull(transaction.getTime());
    }

    @Test
    @Order(9)
    void setTime() {
        Date date = new Date();
        transaction.setTime(date);
        assertEquals(date, transaction.getTime());
    }
}