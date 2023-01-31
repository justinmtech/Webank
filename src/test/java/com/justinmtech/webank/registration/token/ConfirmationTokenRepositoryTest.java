package com.justinmtech.webank.registration.token;

import com.justinmtech.webank.model.User;
import com.justinmtech.webank.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ConfirmationTokenRepositoryTest {

    @Autowired
    private ConfirmationTokenRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveToken() {
        User user = new User("bob", "thorton");
        userRepository.save(user);
        ConfirmationToken token = new ConfirmationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        token.setUser(user);
        repository.save(token);
        assertTrue(repository.findByToken(token.getToken()).isPresent());
    }

}