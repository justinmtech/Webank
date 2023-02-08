package com.justinmtech.webank.exceptions.transaction;

public class TransactionAmountZeroError extends Exception {
    public TransactionAmountZeroError() {
        super("Transaction amount must be greater than zero");
    }
}
