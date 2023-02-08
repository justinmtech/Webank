package com.justinmtech.webank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;

/**
 * The container of user account data
 */
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

    private final static BigDecimal DEFAULT_BALANCE = BigDecimal.valueOf(50);
    private final static String EMPTY_FIELD = "n/a";
    private final static String ADMIN_USERNAME = "admin";

    public User() {
        setBalance(DEFAULT_BALANCE);
        setRole(Role.USER);
        setRole(Role.USER);
        setAccountEnabled(false);
    }

    public User(@NotNull String username, @NotNull String password, @NotNull BigDecimal balance,
                @Nullable String firstName, @Nullable String lastName, @Nullable String phoneNumber,
                @Nullable Role role, boolean accountEnabled) {
        setUsername(username);
        setPassword(password);
        setBalance(balance);
        setFirstName(firstName != null ? firstName : EMPTY_FIELD);
        setLastName(lastName != null ? lastName : EMPTY_FIELD);
        setPhoneNumber(phoneNumber != null ? phoneNumber : EMPTY_FIELD);
        setRole(role != null ? role : Role.USER);
        setAccountEnabled(accountEnabled);
    }

    public User(@NotNull String username, @NotNull String password) {
        setUsername(username);
        setPassword(password);
        setBalance(BigDecimal.ZERO);
        setFirstName(EMPTY_FIELD);
        setLastName(EMPTY_FIELD);
        setPhoneNumber(EMPTY_FIELD);
        setRole(username.equals(ADMIN_USERNAME) ? this.role = Role.ADMIN : Role.USER);
        setAccountEnabled(false);
    }

    public User(@NotNull String username, @NotNull String password, @Nullable String firstName, @Nullable String lastName, @Nullable String phoneNumber) {
        setUsername(username);
        setPassword(password);
        setBalance(DEFAULT_BALANCE);
        setFirstName(firstName != null ? firstName : EMPTY_FIELD);
        setLastName(lastName != null ? lastName : EMPTY_FIELD);
        setPhoneNumber(phoneNumber != null ? phoneNumber : EMPTY_FIELD);
        setRole(username.equals(ADMIN_USERNAME) ? this.role = Role.ADMIN : Role.USER);
        setAccountEnabled(false);

    }

    public User(String username, String password, Role role) {
        setUsername(username);
        setPassword(password);
        setBalance(DEFAULT_BALANCE);
        setFirstName(EMPTY_FIELD);
        setLastName(EMPTY_FIELD);
        setPhoneNumber(EMPTY_FIELD);
        setRole(role);
        setAccountEnabled(false);
    }

    public User(User user) {
        setUsername(user.getUsername());
        setPassword(user.getPassword());
        setBalance(user.getBalance());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setPhoneNumber(user.getPhoneNumber());
        setRole(user.getRole());
        setAccountEnabled(user.isEnabled());
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
        return role != null ? this.role : Role.USER;
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
        return Collections.singleton(new SimpleGrantedAuthority(getRole().name()));
    }

    public void setAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
