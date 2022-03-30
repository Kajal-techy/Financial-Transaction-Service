package com.bench.project.bench.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FinancialTransactionExceptionHandler {

    @ExceptionHandler(FinancialTransactionNotExistsException.class)
    public ResponseEntity<Object> financialTransactionDoesNotExists() {
        return new ResponseEntity<>("Financial Transaction Does not exists", HttpStatus.NOT_FOUND);
    }
}
