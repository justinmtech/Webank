package com.justinmtech.webank.service;

import com.justinmtech.webank.exceptions.transaction.BalanceNotUpdatedError;
import com.justinmtech.webank.exceptions.transaction.InsufficientFundsError;
import com.justinmtech.webank.exceptions.transaction.TransactionAccountNotFound;
import com.justinmtech.webank.exceptions.transaction.TransactionMember;
import com.justinmtech.webank.exceptions.user.UserNotFoundError;
import com.justinmtech.webank.model.Transaction;
import com.justinmtech.webank.model.User;
import com.justinmtech.webank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Create and resolve transactions between users
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

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


    @SuppressWarnings("UnusedReturnValue")
    @Async
    private CompletableFuture<Transaction> createNewTransaction(String from, String to, BigDecimal amount) throws TransactionAccountNotFound {
        Transaction transaction = new Transaction(from, to, amount);
        if (!getUserService().userExists(from)) throw new TransactionAccountNotFound(TransactionMember.SENDER, from);
        if (!getUserService().userExists(to)) throw new TransactionAccountNotFound(TransactionMember.RECEIVER, to);
        Transaction transactionSaved = getTransactionRepository().save(transaction);
        return CompletableFuture.completedFuture(transactionSaved);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean handleTransaction(Transaction transaction) throws Exception {
        String receiverUsername = transaction.getReceiver();
        String senderUsername = transaction.getSender();

        if (userDoesNotExist(receiverUsername)) {
            throw new UserNotFoundError(receiverUsername);
        }
        if (userDoesNotExist(senderUsername)) {
            throw new UserNotFoundError(senderUsername);
        }
        if (insufficientFunds(transaction)) {
            throw new InsufficientFundsError(TransactionMember.SENDER, transaction.getSender());
        }

        BigDecimal previousSenderBalance = getUserService().getUserBalance(senderUsername);
        BigDecimal newSenderBalance = previousSenderBalance.subtract(transaction.getAmount()).setScale(2, RoundingMode.UNNECESSARY);
        boolean senderBalanceUpdateSuccess = handleBalanceUpdate(senderUsername, newSenderBalance);

        BigDecimal previousReceiverBalance = getUserService().getUserBalance(receiverUsername);
        BigDecimal newReceiverBalance = previousReceiverBalance.add(transaction.getAmount()).setScale(2, RoundingMode.UNNECESSARY);
        boolean receiverBalanceUpdateSuccess = handleBalanceUpdate(receiverUsername, newReceiverBalance);

        //Revert balances if failure
        if (!senderBalanceUpdateSuccess || !receiverBalanceUpdateSuccess) {
            handleBalanceUpdate(senderUsername, previousSenderBalance);
            handleBalanceUpdate(receiverUsername, previousReceiverBalance);
            return false;
        } else {
            createNewTransaction(transaction.getSender(), transaction.getReceiver(), transaction.getAmount());
            return true;
        }
    }

    private boolean handleBalanceUpdate(String username, BigDecimal newBalance) throws BalanceNotUpdatedError, UserNotFoundError {
        return setBalance(username, newBalance);
    }

    private boolean userDoesNotExist(String username) {
        return !getUserService().userExists(username);
    }

    private boolean insufficientFunds(Transaction transaction) throws UserNotFoundError {
        return !userHasEnoughFunds(transaction.getSender(), transaction.getAmount());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private boolean setBalance(String username, BigDecimal amount) throws UserNotFoundError, BalanceNotUpdatedError {
        if (getUserService().userExists(username)) {
            User user = getUserService().getUser(username).join().get();
            user.setBalance(amount);
            if (user.getBalance().equals(amount)) {
                getUserService().updateUser(user);
                return true;
            } else {
                throw new BalanceNotUpdatedError(username);
            }
        } else {
            throw new UserNotFoundError(username);
        }
    }

    private boolean userHasEnoughFunds(String username, BigDecimal transactionAmount) throws UserNotFoundError {
        if (username == null) return false;
        if (transactionAmount == null) return false;
        Optional<User> user = getUserService().getUser(username).join();
        if (user.isPresent()) {
            BigDecimal currentBalance = user.get().getBalance();
            int comparison = currentBalance.compareTo(transactionAmount);
            return comparison >= 0;
        } else {
            throw new UserNotFoundError(username);
        }
    }

    private TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    private UserService getUserService() {
        return userService;
    }
}
