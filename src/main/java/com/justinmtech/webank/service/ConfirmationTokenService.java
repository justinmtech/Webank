package com.justinmtech.webank.service;

import com.justinmtech.webank.model.ConfirmationToken;
import com.justinmtech.webank.repository.ConfirmationTokenRepository;
import org.jetbrains.annotations.NotNull;
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

    /**
     * @param confirmationToken The confirmation token object
     * @return The confirmation token as it was saved
     */
    @Transactional
    public ConfirmationToken saveConfirmationToken(@NotNull ConfirmationToken confirmationToken) {
        return getConfirmationTokenRepository().save(confirmationToken);
    }

    /**
     * @param tokenId The id of the token
     * @return An optional of ConfirmationToken
     */
    @Transactional
    public Optional<ConfirmationToken> getToken(@NotNull String tokenId) {
        return getConfirmationTokenRepository().findByToken(tokenId);
    }

    /**
     * @param tokenId The id of the token
     * @return An optional of the ConfirmationToken that was deleted
     */
    @Transactional
    public Optional<ConfirmationToken> deleteToken(@NotNull String tokenId) {
        return getConfirmationTokenRepository().deleteByToken(tokenId);
    }

    private ConfirmationTokenRepository getConfirmationTokenRepository() {
        return confirmationTokenRepository;
    }
}
