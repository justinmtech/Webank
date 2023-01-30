package com.justinmtech.webank.exceptions.transaction;

public class BalanceNotUpdatedError extends Exception {

    public BalanceNotUpdatedError(String username) {
        super(username + "'s balance could not be updated.");
    }
}
