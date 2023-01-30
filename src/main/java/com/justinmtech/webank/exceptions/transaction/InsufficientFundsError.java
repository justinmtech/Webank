package com.justinmtech.webank.exceptions.transaction;

public class InsufficientFundsError extends Exception {
    public InsufficientFundsError(TransactionMember transactionMember, String username) {
        super(transactionMember.name() + " (" + username + ")" + " does not have enough funds to complete this transaction.");
    }
}
