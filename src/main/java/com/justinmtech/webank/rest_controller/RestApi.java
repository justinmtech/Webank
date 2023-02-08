package com.justinmtech.webank.rest_controller;

import com.justinmtech.webank.model.Transaction;
import com.justinmtech.webank.model.User;
import com.justinmtech.webank.service.TransactionService;
import com.justinmtech.webank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A RESTful API for obtaining data through web requests
 */
//TODO Setup security/permissions
@RestController
public class RestApi {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/v1/getUser/{username}")
    public CompletableFuture<Optional<User>> getUser(@PathVariable(name = "username") String username) {
        return getUserService().getUser(username);
    }

    @GetMapping("/api/v1/getUsers")
    public CompletableFuture<List<User>> getUsers() {
        return getUserService().getUsers();
    }

    @GetMapping("/api/v1/getTransactions")
    public CompletableFuture<List<Transaction>> getTransactions() {
        return getTransactionService().getAll();
    }

    @GetMapping("/api/v1/getTransactions/{username}")
    public CompletableFuture<List<Transaction>> getTransactions(@PathVariable(value = "username") String username) throws ExecutionException, InterruptedException {
        return getTransactionService().getAllTransactionsByUsername(username);
    }

    public UserService getUserService() {
        return userService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }
}
