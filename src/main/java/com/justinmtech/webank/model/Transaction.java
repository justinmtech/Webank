package com.justinmtech.webank.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Holds transaction data such as id, sender, receiver, amount and time.
 */
@Entity
@Table(name = "webank_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "sender_username", nullable = false)
    private String sender;
    @Column(name = "receiver_username", nullable = false)
    private String receiver;
    private BigDecimal amount;
    @Column(nullable = false)
    private Date time;

    public Transaction() {
    }

    public Transaction(Long id, String sender, String receiver, BigDecimal amount, Date time) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        setAmount(amount);
        this.time = time;
    }

    public Transaction(String sender, String receiver, BigDecimal amount) {
        this.sender = sender;
        this.receiver = receiver;
        setAmount(amount);
        this.time = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.UNNECESSARY);
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", amount=" + amount +
                ", time=" + time +
                '}';
    }
}
