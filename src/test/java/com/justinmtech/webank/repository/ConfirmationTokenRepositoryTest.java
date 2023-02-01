package com.justinmtech.webank.repository;

import com.justinmtech.webank.model.ConfirmationToken;
import com.justinmtech.webank.model.User;
import com.justinmtech.webank.repository.ConfirmationTokenRepository;
import com.justinmtech.webank.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConfirmationTokenRepositoryTest {

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    private static User user;
    private static String tokenString;
    private static LocalDateTime createdAt;
    private static LocalDateTime expiresAt;
    private static ConfirmationToken confirmationToken;

    @BeforeAll
    static void setup() {
        user = new User("bob", "thorton");
        confirmationToken = new ConfirmationToken();
        tokenString = UUID.randomUUID().toString();
        confirmationToken.setToken(tokenString);
        createdAt = LocalDateTime.now();
        confirmationToken.setCreatedAt(createdAt);
        expiresAt = LocalDateTime.now().plusMinutes(15);
        confirmationToken.setExpiresAt(expiresAt);
        confirmationToken.setUser(user);
    }

    @Test
    @Order(1)
    void canCreateTokens() {
        userRepository.save(user);
        ConfirmationToken savedToken = tokenRepository.save(confirmationToken);
        assertEquals(confirmationToken, savedToken);
    }

    @Test
    @Order(2)
    void canGetToken() {
        Optional<ConfirmationToken> confirmationTokenFetched = tokenRepository.findByToken(tokenString);
        assertTrue(confirmationTokenFetched.isPresent());
        assertEquals(confirmationTokenFetched.get().getToken(), tokenString);
        assertEquals(confirmationTokenFetched.get().getUser().getUsername(), "bob");
    }

    @Test
    @Order(3)
    @Transactional
    void deleteToken() {
        tokenRepository.deleteByToken(tokenString);
        assertTrue(tokenRepository.findByToken(tokenString).isEmpty());
    }

}