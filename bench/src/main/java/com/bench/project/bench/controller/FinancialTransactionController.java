package com.bench.project.bench.controller;

import com.bench.project.bench.exception.FinancialTransactionNotExistsException;
import com.bench.project.bench.service.FinancialTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/financialTransactions")
public class FinancialTransactionController {

    private final FinancialTransactionService financialTransactionService;

    public FinancialTransactionController(FinancialTransactionService financialTransactionService) {
        this.financialTransactionService = financialTransactionService;
    }

    @GetMapping
    public ResponseEntity<Map<LocalDate, Double>> getAllFinancialTransactionByDate() throws InterruptedException, ExecutionException, FinancialTransactionNotExistsException {
        Map<LocalDate, Double> totalAmountByDate = financialTransactionService.getAllFinancialTransaction();
        return new ResponseEntity<>(totalAmountByDate, HttpStatus.OK);

    }
}
