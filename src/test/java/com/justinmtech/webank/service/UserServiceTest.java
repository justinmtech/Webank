package com.justinmtech.webank.service;

import com.justinmtech.webank.exceptions.user.UserAlreadyExistsError;
import com.justinmtech.webank.exceptions.user.UserNotFoundError;
import com.justinmtech.webank.model.User;
import com.justinmtech.webank.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    private static User user;
    private static User user2;

    @Autowired
    private UserRepository userRepository;

    @Autowired UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @BeforeAll
    static void setup() {
        user = new User("justin@gmail.com", "1 2 3 4 5 6 7 8 9 10");
        user2 = new User("justin2@gmail.com", "1 2 3 4 5 6 7 8 9 102");
    }

    @Test
    @Order(1)
    void createUser() throws UserAlreadyExistsError {
        userService.createUser(user.getUsername(), user.getPassword()).join();
        assertTrue(userService.getUser(user.getUsername()).join().isPresent());
        userService.createUser(user2.getUsername(), user2.getPassword()).join();
        assertTrue(userService.getUser(user2.getUsername()).join().isPresent());
    }

    @Test
    @Order(2)
    void userExists() {
        assertTrue(userService.userExists(user.getUsername()));
        assertTrue(userService.userExists(user2.getUsername()));

    }

    @Test
    @Order(3)
    void getUser() {
        assertTrue(userService.getUser(user.getUsername()).join().isPresent());
        assertTrue(userService.userExists(user.getUsername()));
    }

    @Test
    @Order(4)
    void getUserBalance() throws UserNotFoundError {
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY), userService.getUserBalance(user.getUsername()));
    }

    @Test
    @Order(5)
    void getUsers() {
        List<User> users = userService.getUsers().join();
        assertTrue(users.size() > 0);
    }

    @Test
    @Order(6)
    void updateUser() throws UserNotFoundError {
        user.setBalance(BigDecimal.valueOf(100).setScale(2, RoundingMode.UNNECESSARY));
        assertEquals(BigDecimal.valueOf(100).setScale(2, RoundingMode.UNNECESSARY), user.getBalance());
        userService.updateUser(user).join();
        assertEquals(BigDecimal.valueOf(100).setScale(2, RoundingMode.UNNECESSARY), userService.getUser(user.getUsername()).join().get().getBalance());
    }

    @Test
    @Order(7)
    void deleteUser() throws UserNotFoundError {
        userService.deleteUser(user.getUsername()).join();
        assertTrue(userService.getUser(user.getUsername()).join().isEmpty());
        userService.deleteUser(user2.getUsername()).join();
        assertTrue(userService.getUser(user2.getUsername()).join().isEmpty());
    }
}