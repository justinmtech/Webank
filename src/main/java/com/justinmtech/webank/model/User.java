package com.justinmtech.webank.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "webank_user")
public class User implements UserDetails {

    @Id
    private String username;
    private String password;
    private BigDecimal balance;
    @ManyToMany (cascade = CascadeType.ALL)
    @JoinColumns(
            {
                    @JoinColumn(name = "username", referencedColumnName = "sender_username"),
                    @JoinColumn(name = "username", referencedColumnName = "receiver_username")
            }
    )
    List<Transaction> transactions;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Collection<GrantedAuthority> authorities;
    private boolean accountEnabled;

    public User() {
        this.balance = BigDecimal.valueOf(10_000);
        this.authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
        this.accountEnabled = true;
        this.transactions = new ArrayList<>();
    }

    public User(String username, String password, BigDecimal balance,
                String firstName, String lastName, String phoneNumber,
                Collection<GrantedAuthority> authorities, boolean accountEnabled,
                List<Transaction> transactions) {
        setUsername(username);
        this.password = password;
        this.balance = balance;
        setFirstName(firstName);
        setLastName(lastName);
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
        this.accountEnabled = accountEnabled;
        this.transactions = transactions;
    }

    public User(String username, String password) {
        setUsername(username);
        this.password = password;
        this.balance = BigDecimal.ZERO;
        this.firstName = "n/a";
        this.lastName = "n/a";
        this.phoneNumber = "n/a";
        this.authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
        this.accountEnabled = true;
        this.transactions = new ArrayList<>();
    }

    public User(String username, String password, String firstName, String lastName, String phoneNumber) {
        setUsername(username);
        this.password = password;
        this.balance = BigDecimal.valueOf(10_000);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
        this.accountEnabled = true;
        this.transactions = new ArrayList<>();
        this.transactions.add(new Transaction(this, this, BigDecimal.TEN));

    }

    public User(String username, String password, Role role) {
        setUsername(username);
        this.password = password;
        this.balance = BigDecimal.ZERO;
        this.firstName = "n/a";
        this.lastName = "n/a";
        this.phoneNumber = "n/a";
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(role.name()));
        this.accountEnabled = true;
    }

    public User(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.balance = user.getBalance();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.authorities = (Collection<GrantedAuthority>) user.getAuthorities();
        this.accountEnabled = user.isEnabled();
    }

    public List<Transaction> getReceivedTransactions() {
        List<Transaction> receivedTransactions = new ArrayList<>();
        for (Transaction transaction : getTransactions()) {
            if (transaction.getReceiver().equals(this.username)) {
                receivedTransactions.add(transaction);
            }
        }
        return receivedTransactions;
    }

    public List<Transaction> getSentTransactions() {
        List<Transaction> sentTransactions = new ArrayList<>();
        for (Transaction transaction : getTransactions()) {
            if (transaction.getSender().equals(this.username)) {
                sentTransactions.add(transaction);
            }
        }
        return sentTransactions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.trim();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.accountEnabled;
    }

    @Override
    public boolean isEnabled() {
        return this.accountEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
