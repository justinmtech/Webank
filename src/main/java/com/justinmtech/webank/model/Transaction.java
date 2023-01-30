package com.justinmtech.webank.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "webank_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_username", referencedColumnName = "username")
    private User sender;
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_username", referencedColumnName = "username")
    private User receiver;
    private BigDecimal amount;
    private Date time;

    public Transaction() {
    }

    public Transaction(Long id, User sender, User receiver, BigDecimal amount, Date time) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.time = time;
    }

    public Transaction(User sender, User receiver, BigDecimal amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.time = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
