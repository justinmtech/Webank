package com.justinmtech.webank.model;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTest {
    private static User user;

    @BeforeAll
    static void setup() {
        user = new User("test_account", "password");
    }

    @Test
    @Order(1)
    void testConstructor() {
        User user = new User("test_acc  ", "%$(@$*@)(*@)(*C*CCCCj   a s JDJFD A X(X(");
        assertEquals("test_acc", user.getUsername());
        assertEquals("%$(@$*@)(*@)(*C*CCCCj   a s JDJFD A X(X(", user.getPassword());
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY), user.getBalance());
        assertEquals("n/a", user.getFirstName());
        assertEquals("n/a", user.getLastName());
        assertEquals("n/a", user.getPhoneNumber());
    }

    @Test
    @Order(2)
    void getUsername() {
        user.setUsername("test_account");
        assertEquals("test_account", user.getUsername());
    }

    @Test
    @Order(3)
    void setUsername() {
        user.setUsername("new_username");
        assertEquals("new_username", user.getUsername());
        user.setUsername("test_account   ");
        assertEquals("test_account", user.getUsername());
    }

    @Test
    @Order(4)
    void getPassword() {
        user.setPassword("password");
        assertEquals("password", user.getPassword());
    }

    @Test
    @Order(5)
    void setPassword() {
        user.setPassword("new_password_____");
        assertEquals("new_password_____", user.getPassword());
    }

    @Test
    @Order(6)
    void getBalance() {
        user.setBalance(BigDecimal.ZERO);
        BigDecimal balance = user.getBalance();
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY), balance);
    }

    @Test
    @Order(7)
    void setBalance() {
        user.setBalance(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN.setScale(2, RoundingMode.UNNECESSARY), user.getBalance());
        user.setBalance(BigDecimal.valueOf(10_000));
        assertEquals(BigDecimal.valueOf(10_000).setScale(2, RoundingMode.UNNECESSARY), user.getBalance());
        user.setBalance(BigDecimal.valueOf(100_000));
        assertEquals(BigDecimal.valueOf(100_000).setScale(2, RoundingMode.UNNECESSARY), user.getBalance());
        user.setBalance(BigDecimal.valueOf(1_000_000));
        assertEquals(BigDecimal.valueOf(1_000_000).setScale(2, RoundingMode.UNNECESSARY), user.getBalance());
        user.setBalance(BigDecimal.valueOf(25_037));
        assertEquals(BigDecimal.valueOf(25_037).setScale(2, RoundingMode.UNNECESSARY), user.getBalance());
        user.setBalance(BigDecimal.valueOf(10_000));
        assertEquals(BigDecimal.valueOf(10_000).setScale(2, RoundingMode.UNNECESSARY), user.getBalance());
        user.setBalance(BigDecimal.valueOf(1.01).setScale(2, RoundingMode.UNNECESSARY));
        assertEquals(BigDecimal.valueOf(1.01), user.getBalance());
    }

    @Test
    @Order(8)
    void getFirstName() {
        user.setFirstName("n/a");
        assertEquals("n/a", user.getFirstName());
    }

    @Test
    @Order(9)
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
    @Order(10)
    void getLastName() {
        user.setLastName("n/a");
        assertEquals("n/a", user.getLastName());
    }

    @Test
    @Order(11)
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
    @Order(12)
    void getPhoneNumber() {
        user.setPhoneNumber("n/a");
        assertEquals("n/a", user.getPhoneNumber());
    }

    @Test
    @Order(13)
    void setPhoneNumber() {
        user.setPhoneNumber("601-111-0101");
        assertEquals("601-111-0101", user.getPhoneNumber());
    }
}