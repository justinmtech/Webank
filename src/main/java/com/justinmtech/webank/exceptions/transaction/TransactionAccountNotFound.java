package com.justinmtech.webank.exceptions.transaction;

public class TransactionAccountNotFound extends Exception {

    public TransactionAccountNotFound(TransactionMember transactionMember, String username) {
        super(transactionMember.name() + "(" + username + ") could not be found");
    }
    public TransactionAccountNotFound() {
        super("An account involved in this transaction could not be found");
    }
}
