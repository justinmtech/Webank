package com.justinmtech.webank.repository;

import com.justinmtech.webank.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void addUser() {
        User user = new User("test", "pass");
        assertEquals(0, userRepository.findAll().size());
        userRepository.save(user);
        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    void getUser() {
        User user = new User("bobhasalongemail@gmail.com", "p41340981324222");
        userRepository.save(user);
        Optional<User> fetchedUser = userRepository.findById(user.getUsername());
        assertTrue(fetchedUser.isPresent());
    }

    @Test
    void deleteUser() {
        User user = new User("jamm", "!!!as ad f d a as dd f  d fd a");
        userRepository.save(user);
        Optional<User> fetchedUser = userRepository.findById(user.getUsername());
        assertTrue(fetchedUser.isPresent());
        userRepository.deleteById(user.getUsername());
        Optional<User> deletedUser = userRepository.findById(user.getUsername());
        assertTrue(deletedUser.isEmpty());

    }

    @Test
    void updateUser() {
        User user = new User("updateme", "!!!as ad f d a as dd f  d fd a");
        userRepository.save(user);
        Optional<User> fetchedUser = userRepository.findById(user.getUsername());
        assertTrue(fetchedUser.isPresent());
        user.setBalance(BigDecimal.valueOf(12).stripTrailingZeros());
        user.setPassword("new password");
        user.setPhoneNumber("601-222-4434");
        userRepository.save(user);
        Optional<User> updatedUser = userRepository.findById("updateme");
        assertTrue(updatedUser.isPresent());
        assertEquals(BigDecimal.valueOf(12).stripTrailingZeros(), updatedUser.get().getBalance().stripTrailingZeros());
        assertEquals("new password", updatedUser.get().getPassword());
        assertEquals("601-222-4434", updatedUser.get().getPhoneNumber());
    }
}