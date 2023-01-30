package com.justinmtech.webank.service;

import com.justinmtech.webank.model.User;
import com.justinmtech.webank.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Async
    public CompletableFuture<Optional<User>> getUser(String username) {
        Optional<User> user = userRepository.findById(username);
        return CompletableFuture.completedFuture(user);
    }

    @Async
    public CompletableFuture<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return CompletableFuture.completedFuture(users);
    }

    @Async
    public void deleteUser(String username) throws Exception {
        if (userRepository.findById(username).isPresent()) {
            userRepository.deleteById(username);
        } else {
            throw new Exception("User does not exist");
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    @Async
    public CompletableFuture<User> createUser(String username, String password) throws Exception {
        if (userRepository.findById(username).isEmpty()) {
            User user = userRepository.save(new User(username, getPasswordEncoder().encode(password)));
            return CompletableFuture.completedFuture(user);
        } else {
            throw new Exception("User already exists");
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    @Async
    public CompletableFuture<User> createUser(String username, String password, String firstName, String lastName, String phoneNumber) throws Exception {
        if (userRepository.findById(username).isEmpty()) {
            User user = userRepository.save(new User(username, getPasswordEncoder().encode(password), firstName, lastName, phoneNumber));
            return CompletableFuture.completedFuture(user);
        } else {
            throw new Exception("User already exists");
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    @Async
    public CompletableFuture<User> updateUser(User user) throws Exception {
        if (userRepository.findById(user.getUsername()).isPresent()) {
            User updatedUser = userRepository.save(user);
            return CompletableFuture.completedFuture(updatedUser);
        } else {
            throw new Exception("User not found");
        }
    }

    public boolean userExists(String username) {
        return userRepository.findById(username).isPresent();
    }

    public User getCurrentAuthenticatedUser() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> user = getUser(username).join();
            return user.orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
