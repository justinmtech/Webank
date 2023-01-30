package com.justinmtech.webank.exceptions.user;

public class UserNotFoundError extends Exception {
    public UserNotFoundError(String username) {
        super("The user '" + username + "' does not exist");
    }
}
