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

    /**
     * @param username User id of the account
     * @return Optional User object as a completable future
     */
    @GetMapping("/api/v1/getUser/{username}")
    public CompletableFuture<Optional<User>> getUser(@PathVariable(name = "username") String username) {
        return getUserService().getUser(username);
    }

    /**
     * @return List of all users as a completable future
     */
    @GetMapping("/api/v1/getUsers")
    public CompletableFuture<List<User>> getUsers() {
        return getUserService().getUsers();
    }

    /**
     * @return List of all transactions as a completable future
     */
    @GetMapping("/api/v1/getTransactions")
    public CompletableFuture<List<Transaction>> getTransactions() {
        return getTransactionService().getAll();
    }

    /**
     * @param username User id to search for
     * @return List of transactions the user was involved in
     */
    @GetMapping("/api/v1/getTransactions/{username}")
    public CompletableFuture<List<Transaction>> getTransactions(@PathVariable(value = "username") String username) {
        return getTransactionService().getAllTransactionsByUsername(username);
    }

    private UserService getUserService() {
        return userService;
    }

    private TransactionService getTransactionService() {
        return transactionService;
    }
}
