package com.bench.project.bench.exception;

public class FinancialTransactionNotExistsException extends Exception {

    public FinancialTransactionNotExistsException() {
        super("Financial Transaction Does not exists");
    }
}
