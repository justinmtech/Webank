package com.justinmtech.webank.service;

import com.justinmtech.webank.model.User;
import com.justinmtech.webank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Obtain and map User objects to UserDetails objects
 */
@Service
public class WebankUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = getUserRepository().findById(username);
        if (user.isPresent()) {
            return user.map(User::new).get();
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
