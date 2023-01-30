package com.justinmtech.webank.service;

import com.justinmtech.webank.model.Transaction;
import com.justinmtech.webank.model.User;
import com.justinmtech.webank.repository.TransactionRepository;
import com.justinmtech.webank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    @Async
    public CompletableFuture<Optional<Transaction>> getTransactionById(Long id) {
        return CompletableFuture.completedFuture(getTransactionRepository().findById(id));
    }

    @Async
    public CompletableFuture<List<Transaction>> getAll() {
        List<Transaction> transactions = getTransactionRepository().findAll();
        return CompletableFuture.completedFuture(transactions);
    }

    @Async
    public CompletableFuture<List<Transaction>> getTransactionsBySender(String username) {
        List<Transaction> transactions = getTransactionRepository().findBySender(username);
        return CompletableFuture.completedFuture(transactions);
    }

    @Async
    public CompletableFuture<List<Transaction>> getTransactionsByReceiver(String username) {
        List<Transaction> transactions = getTransactionRepository().findByReceiver(username);
        return CompletableFuture.completedFuture(transactions);
    }

    @Async
    public CompletableFuture<List<Transaction>> getAllTransactionsByUsername(String username) throws ExecutionException, InterruptedException {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(getTransactionsBySender(username).get());
        transactions.addAll(getTransactionsByReceiver(username).get());
        return CompletableFuture.completedFuture(transactions);
    }


    @Async
    private void createNewTransaction(String from, String to, BigDecimal amount) throws Exception {
        Transaction transaction = new Transaction(from, to, amount);
        if (!getUserService().userExists(from)) throw new Exception("The sender does not exist: " + from);
        if (!getUserService().userExists(to)) throw new Exception("The receiver does not exist: " + to);
        getTransactionRepository().save(transaction);
    }

    public void handleTransaction(Transaction transaction) throws Exception {
        if (userHasEnoughFunds(transaction.getSender(), transaction.getAmount())) {
            if (getUserService().userExists(transaction.getReceiver())) {
                Optional<User> sender = getUserService().getUser(transaction.getSender()).join();
                Optional<User> receiver = getUserService().getUser(transaction.getReceiver()).join();

                if (sender.isPresent() && receiver.isPresent()) {
                    BigDecimal newSenderBalance = sender.get().getBalance().subtract(transaction.getAmount());
                    BigDecimal newReceiverBalance = receiver.get().getBalance().add(transaction.getAmount());
                    setBalance(transaction.getSender(), newSenderBalance);
                    setBalance(transaction.getReceiver(), newReceiverBalance);
                    createNewTransaction(transaction.getSender(), transaction.getReceiver(), transaction.getAmount());
                } else {
                    throw new Exception("Sender or receiver not found.");
                }
            } else {
                throw new Exception("Sender does not exist");
            }
        } else {
            throw new Exception("Sender does not have enough funds.");
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private void setBalance(String username, BigDecimal amount) throws Exception {
        if (getUserService().userExists(username)) {
            User user = getUserService().getUser(username).join().get();
            user.setBalance(amount);
            if (user.getBalance().equals(amount)) {
                getUserService().updateUser(user);
            } else {
                throw new Exception("The balance could not be updated");
            }
        } else {
            throw new Exception("User not found: " + username);
        }
    }

    private boolean userHasEnoughFunds(String username, BigDecimal transactionAmount) throws Exception {
        if (username == null) return false;
        if (transactionAmount == null) return false;
        Optional<User> user = getUserService().getUser(username).join();
        if (user.isPresent()) {
            BigDecimal currentBalance = user.get().getBalance();
            int comparison = currentBalance.compareTo(transactionAmount);
            return comparison >= 0;
        } else {
            throw new Exception("That user could not be found");
        }
    }

    private TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    private UserService getUserService() {
        return userService;
    }
}
