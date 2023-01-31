package com.justinmtech.webank.registration.token;

import com.justinmtech.webank.model.ConfirmationToken;
import com.justinmtech.webank.model.User;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConfirmationTokenTest {

    private static ConfirmationToken confirmationToken;
    private static LocalDateTime createdAt;
    private static LocalDateTime expiresAt;
    private static User user;
    private static String tokenString;

    @BeforeAll
    static void setup() {
        tokenString = UUID.randomUUID().toString();
        user = new User("justin.mitchell@mail.com", "! * !* $)(!*# long password ! ! ! ! !");
        createdAt = LocalDateTime.now();
        expiresAt = LocalDateTime.now().plusMinutes(15);
        confirmationToken = new ConfirmationToken(tokenString, createdAt, expiresAt, user);
    }

    @Test
    @Order(1)
    void getToken() {
        assertEquals(tokenString, confirmationToken.getToken());
    }

    @Test
    @Order(2)
    void setToken() {
        assertEquals(tokenString, confirmationToken.getToken());
        String secondTokenString = UUID.randomUUID().toString();
        confirmationToken.setToken(secondTokenString);
        assertEquals(secondTokenString, confirmationToken.getToken());
    }

    @Test
    @Order(3)
    void getCreatedAt() {
        assertEquals(createdAt, confirmationToken.getCreatedAt());
    }

    @Test
    @Order(4)
    void setCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        confirmationToken.setCreatedAt(now);
        assertEquals(now, confirmationToken.getCreatedAt());
    }

    @Test
    @Order(5)
    void getExpiresAt() {
        assertEquals(expiresAt, confirmationToken.getExpiresAt());
    }

    @Test
    @Order(6)
    void setExpiresAt() {
        LocalDateTime now = LocalDateTime.now();
        confirmationToken.setExpiresAt(now);
        assertEquals(now, confirmationToken.getExpiresAt());
    }

    @Test
    @Order(7)
    void getUser() {
        assertEquals(user, confirmationToken.getUser());
    }

    @Test
    @Order(8)
    void setUser() {
        User user = new User("test", "pass");
        confirmationToken.setUser(user);
        assertEquals(user, confirmationToken.getUser());
    }
}