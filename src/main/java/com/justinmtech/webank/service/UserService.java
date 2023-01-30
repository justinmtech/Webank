package com.justinmtech.webank.service;

import com.justinmtech.webank.model.User;
import com.justinmtech.webank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Optional<User> getUser(String username) {
        return userRepository.findById(username);
    }

    public void deleteUser(String username) throws Exception {
        if (userRepository.findById(username).isPresent()) {
            userRepository.deleteById(username);
        } else {
            throw new Exception("User does not exist");
        }
    }

    public User createUser(String username, String password) throws Exception {
        if (userRepository.findById(username).isEmpty()) {
            return userRepository.save(new User(username, getPasswordEncoder().encode(password)));
        } else {
            throw new Exception("User already exists");
        }
    }

    public User createUser(String username, String password, String firstName, String lastName, String phoneNumber) throws Exception {
        if (userRepository.findById(username).isEmpty()) {
            return userRepository.save(new User(username, getPasswordEncoder().encode(password), firstName, lastName, phoneNumber));
        } else {
            throw new Exception("User already exists");
        }
    }

    public User updateUser(User user) throws Exception {
        if (userRepository.findById(user.getUsername()).isPresent()) {
            return userRepository.save(user);
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
            Optional<User> user = getUser(username);
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
