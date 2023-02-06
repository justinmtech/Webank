package com.justinmtech.webank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "webank_user")
public class User implements UserDetails {

    @Id
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, columnDefinition = "decimal default 0")
    private BigDecimal balance;
    @Column(nullable = false, columnDefinition = "varchar(255) default 'n/a'")
    private String firstName;
    @Column(nullable = false, columnDefinition = "varchar(255) default 'n/a'")
    private String lastName;
    @Column(nullable = false, columnDefinition = "varchar(255) default 'n/a'")
    private String phoneNumber;
    @Column(nullable = false, columnDefinition = "integer default 0")
    private Role role;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean accountEnabled;

    public User() {
        this.balance = BigDecimal.ZERO;
        this.role = Role.USER;
        this.accountEnabled = false;
    }

    public User(String username, String password, BigDecimal balance,
                String firstName, String lastName, String phoneNumber,
                Role role, boolean accountEnabled) {
        setUsername(username);
        this.password = password;
        setBalance(balance);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        this.role = role;
        this.accountEnabled = accountEnabled;
    }

    public User(String username, String password) {
        setUsername(username);
        this.password = password;
        setBalance(BigDecimal.ZERO);
        this.firstName = "n/a";
        this.lastName = "n/a";
        this.phoneNumber = "n/a";
        if (username.equals("admin")) {
            this.role = Role.ADMIN;
        } else {
            this.role = Role.USER;
        }
        this.accountEnabled = false;
    }

    public User(String username, String password, String firstName, String lastName, String phoneNumber) {
        setUsername(username);
        this.password = password;
        setBalance(BigDecimal.ZERO);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        if (username.equals("admin")) {
            this.role = Role.ADMIN;
        } else {
            this.role = Role.USER;
        }
        this.accountEnabled = false;

    }

    public User(String username, String password, Role role) {
        setUsername(username);
        this.password = password;
        setBalance(BigDecimal.ZERO);
        this.firstName = "n/a";
        this.lastName = "n/a";
        this.phoneNumber = "n/a";
        this.role = role;
        this.accountEnabled = false;
    }

    public User(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.balance = user.getBalance();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole();
        this.accountEnabled = user.isEnabled();
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
        return balance.setScale(2, RoundingMode.UNNECESSARY);
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance.setScale(2, RoundingMode.UNNECESSARY);
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

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        if (getRole() == Role.ADMIN) {
            return Collections.singleton(new SimpleGrantedAuthority(Role.ADMIN.name()));
        } else {
            return Collections.singleton(new SimpleGrantedAuthority(Role.USER.name()));
        }
    }

    public void setAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
