package com.justinmtech.webank.service;

import com.justinmtech.webank.model.ConfirmationToken;
import com.justinmtech.webank.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Create, save and fetch ConfirmationToken objects from the JPA repository
 */
@SuppressWarnings("UnusedReturnValue")
@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Transactional
    public ConfirmationToken saveConfirmationToken(ConfirmationToken confirmationToken) {
        return getConfirmationTokenRepository().save(confirmationToken);
    }

    @Transactional
    public Optional<ConfirmationToken> getToken(String token) {
        return getConfirmationTokenRepository().findByToken(token);
    }

    @Transactional
    public Optional<ConfirmationToken> deleteToken(String token) {
        return getConfirmationTokenRepository().deleteByToken(token);
    }

    public ConfirmationTokenRepository getConfirmationTokenRepository() {
        return confirmationTokenRepository;
    }
}
