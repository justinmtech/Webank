package com.justinmtech.webank.registration.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken saveConfirmationToken(ConfirmationToken confirmationToken) {
        return getConfirmationTokenRepository().save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return getConfirmationTokenRepository().findByToken(token);
    }

    public Optional<ConfirmationToken> deleteToken(String token) {
        return getConfirmationTokenRepository().deleteByToken(token);
    }

    public ConfirmationTokenRepository getConfirmationTokenRepository() {
        return confirmationTokenRepository;
    }
}
