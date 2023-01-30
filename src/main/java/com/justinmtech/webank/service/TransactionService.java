package com.justinmtech.webank.service;

import com.justinmtech.webank.model.Transaction;
import com.justinmtech.webank.model.User;
import com.justinmtech.webank.repository.TransactionRepository;
import com.justinmtech.webank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    public Optional<Transaction> getTransactionById(Long id) {
        return getTransactionRepository().findById(id);
    }

    public List<Transaction> getAll() {
        return getTransactionRepository().findAll();
    }

    public List<Transaction> getTransactionsBySender(String username) {
        return getTransactionRepository().findBySender(username);
    }

    public List<Transaction> getTransactionsByReceiver(String username) {
        return getTransactionRepository().findByReceiver(username);
    }

    public List<Transaction> getAllTransactionsByUsername(String username) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(getTransactionsBySender(username));
        transactions.addAll(getTransactionsByReceiver(username));
        return transactions;
    }

    private void createNewTransaction(String from, String to, BigDecimal amount) throws Exception {
        Transaction transaction = new Transaction(from, to, amount);
        if (!getUserService().userExists(from)) throw new Exception("The sender does not exist: " + from);
        if (!getUserService().userExists(to)) throw new Exception("The receiver does not exist: " + to);
        transactionRepository.save(transaction);
    }

    public void handleTransaction(Transaction transaction) throws Exception {
        if (userHasEnoughFunds(transaction.getSender(), transaction.getAmount())) {
            if (getUserService().userExists(transaction.getReceiver())) {
                Optional<User> sender = getUserService().getUser(transaction.getSender());
                Optional<User> receiver = getUserService().getUser(transaction.getReceiver());

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
            User user = getUserService().getUser(username).get();
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
        Optional<User> user = getUserService().getUser(username);
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
