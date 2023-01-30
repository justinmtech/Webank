package com.justinmtech.webank.exceptions.user;

public class UserAlreadyExistsError extends Exception {
    public UserAlreadyExistsError() {
        super("User already exists");
    }
}
