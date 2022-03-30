package com.bench.project.bench.service;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@ControllerAdvice
public interface FinancialTransactionService {

    Map<LocalDate, Double> getAllFinancialTransaction() throws InterruptedException, ExecutionException;
}
