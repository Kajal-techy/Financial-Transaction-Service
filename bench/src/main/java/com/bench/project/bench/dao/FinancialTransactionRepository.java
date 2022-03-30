package com.bench.project.bench.dao;

import com.bench.project.bench.entity.FinancialTransactionsResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FinancialTransactionRepository {

    List<ResponseEntity<FinancialTransactionsResponse>> fetchAllTransactions() throws InterruptedException, ExecutionException;
}
