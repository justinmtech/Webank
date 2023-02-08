package com.justinmtech.webank.service;

import com.justinmtech.webank.exceptions.user.UserAlreadyExistsError;
import com.justinmtech.webank.exceptions.user.UserNotFoundError;
import com.justinmtech.webank.model.User;
import com.justinmtech.webank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Create, fetch and update users
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Async
    public CompletableFuture<Optional<User>> getUser(String username) {
        Optional<User> user = userRepository.findById(username);
        return CompletableFuture.completedFuture(user);
    }

    public BigDecimal getUserBalance(String username) throws UserNotFoundError {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) throw new UserNotFoundError(username);
        return user.get().getBalance();
    }

    @Async
    public CompletableFuture<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return CompletableFuture.completedFuture(users);
    }

    @SuppressWarnings("unused")
    @Async
    public CompletableFuture<String> deleteUser(String username) throws UserNotFoundError {
        if (userRepository.findById(username).isPresent()) {
            userRepository.deleteById(username);
            return CompletableFuture.completedFuture(username);
        } else {
            throw new UserNotFoundError(username);
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    @Async
    public CompletableFuture<User> createUser(String username, String password) throws UserAlreadyExistsError {
        if (userRepository.findById(username).isEmpty()) {
            User user = userRepository.save(new User(username, getPasswordEncoder().encode(password)));
            return CompletableFuture.completedFuture(user);
        } else {
            throw new UserAlreadyExistsError();
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    @Async
    public CompletableFuture<User> createUser(User user) throws UserAlreadyExistsError {
        if (userRepository.findById(user.getUsername()).isEmpty()) {
            user.setPassword(getPasswordEncoder().encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            return CompletableFuture.completedFuture(savedUser);
        } else {
            throw new UserAlreadyExistsError();
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    @Async
    public CompletableFuture<User> createUser(String username, String password, String firstName, String lastName, String phoneNumber) throws UserAlreadyExistsError {
        if (userRepository.findById(username).isEmpty()) {
            User user = new User(username, getPasswordEncoder().encode(password), firstName, lastName, phoneNumber);
            user.setBalance(BigDecimal.valueOf(100));
            userRepository.save(user);

            return CompletableFuture.completedFuture(user);
        } else {
            throw new UserAlreadyExistsError();
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    @Async
    public CompletableFuture<User> updateUser(User user) throws UserNotFoundError {
        if (userRepository.findById(user.getUsername()).isPresent()) {
            User updatedUser = userRepository.save(user);
            return CompletableFuture.completedFuture(updatedUser);
        } else {
            throw new UserNotFoundError(user.getUsername());
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

    private BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    private ConfirmationTokenService getConfirmationTokenService() {
        return confirmationTokenService;
    }
}
