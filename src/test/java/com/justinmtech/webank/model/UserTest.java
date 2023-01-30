package com.justinmtech.webank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static User user;

    @BeforeAll
    static void setup() {
        user = new User("test_account", "password");
    }

    @Test
    void testConstructor() {
        User user = new User("test_acc  ", "%$(@$*@)(*@)(*C*CCCCj   a s JDJFD A X(X(");
        assertEquals("test_acc", user.getUsername());
        assertEquals("%$(@$*@)(*@)(*C*CCCCj   a s JDJFD A X(X(", user.getPassword());
        assertEquals(BigDecimal.ZERO, user.getBalance());
        assertEquals("n/a", user.getFirstName());
        assertEquals("n/a", user.getLastName());
        assertEquals("n/a", user.getPhoneNumber());
    }

    @Test
    void getUsername() {
        user.setUsername("test_account");
        assertEquals("test_account", user.getUsername());
    }

    @Test
    void setUsername() {
        user.setUsername("new_username");
        assertEquals("new_username", user.getUsername());
        user.setUsername("test_account   ");
        assertEquals("test_account", user.getUsername());
    }

    @Test
    void getPassword() {
        user.setPassword("password");
        assertEquals("password", user.getPassword());
    }

    @Test
    void setPassword() {
        user.setPassword("new_password_____");
        assertEquals("new_password_____", user.getPassword());
    }

    @Test
    void getBalance() {
        user.setBalance(BigDecimal.ZERO);
        BigDecimal balance = user.getBalance();
        assertEquals(BigDecimal.ZERO, balance);
    }

    @Test
    void setBalance() {
        user.setBalance(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, user.getBalance());
        user.setBalance(BigDecimal.valueOf(10_000));
        assertEquals(BigDecimal.valueOf(10_000), user.getBalance());
        user.setBalance(BigDecimal.valueOf(100_000));
        assertEquals(BigDecimal.valueOf(100_000), user.getBalance());
        user.setBalance(BigDecimal.valueOf(1_000_000));
        assertEquals(BigDecimal.valueOf(1_000_000), user.getBalance());
        user.setBalance(BigDecimal.valueOf(25_037));
        assertEquals(BigDecimal.valueOf(25_037), user.getBalance());
        user.setBalance(BigDecimal.valueOf(10_000));
        assertEquals(BigDecimal.valueOf(10_000), user.getBalance());
    }

    @Test
    void getFirstName() {
        user.setFirstName("n/a");
        assertEquals("n/a", user.getFirstName());
    }

    @Test
    void setFirstName() {
        user.setFirstName("Justin");
        assertEquals("Justin", user.getFirstName());
        user.setFirstName("  Justin");
        assertEquals("Justin", user.getFirstName());
        user.setFirstName("Justin  ");
        assertEquals("Justin", user.getFirstName());
        user.setFirstName("  Justin  ");
        assertEquals("Justin", user.getFirstName());
        user.setFirstName(" l Justin l ");
        assertEquals("l Justin l", user.getFirstName());
    }

    @Test
    void getLastName() {
        user.setLastName("n/a");
        assertEquals("n/a", user.getLastName());
    }

    @Test
    void setLastName() {
        user.setLastName("Mitchell");
        assertEquals("Mitchell", user.getLastName());
        user.setLastName(" Mitchell");
        assertEquals("Mitchell", user.getLastName());
        user.setLastName("Mitchell ");
        assertEquals("Mitchell", user.getLastName());
        user.setLastName("    Mitchell   ");
        assertEquals("Mitchell", user.getLastName());
    }

    @Test
    void getPhoneNumber() {
        user.setPhoneNumber("n/a");
        assertEquals("n/a", user.getPhoneNumber());
    }

    @Test
    void setPhoneNumber() {
        user.setPhoneNumber("601-111-0101");
        assertEquals("601-111-0101", user.getPhoneNumber());
    }
}