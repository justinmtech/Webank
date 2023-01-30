package com.justinmtech.webank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

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
        //transaction = new Transaction(sender, receiver, amount);
    }

/*    @Test
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
    }*/

    @Test
    void getId() {
    }

    @Test
    void setId() {
    }

/*    @Test
    void getSender() {
        transaction.setSender(sender);
        assertEquals(sender, transaction.getSender());
    }*/

/*    @Test
    void setSender() {
        String sender = "testaccount@gmail.com";
        transaction.setSender(sender);
        assertEquals(sender, transaction.getSender());
    }*/

/*    @Test
    void getReceiver() {
        transaction.setReceiver(receiver);
        assertEquals(receiver, transaction.getReceiver());
    }

    @Test
    void setReceiver() {
        String receiver = "testaccount2@gmail.com";
        transaction.setReceiver(receiver);
        assertEquals(receiver, transaction.getReceiver());
    }*/

    @Test
    void getAmount() {
        transaction.setAmount(amount);
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    void setAmount() {
        BigDecimal amountTest = BigDecimal.valueOf(101010106);
        transaction.setAmount(amountTest);
        assertEquals(amountTest, transaction.getAmount());
    }

    @Test
    void getTime() {
        assertNotNull(transaction.getTime());
    }

    @Test
    void setTime() {
        Date date = new Date();
        transaction.setTime(date);
        assertEquals(date, transaction.getTime());
    }
}