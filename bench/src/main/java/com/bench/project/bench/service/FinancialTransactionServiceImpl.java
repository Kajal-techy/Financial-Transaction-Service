package com.bench.project.bench.service;

import com.bench.project.bench.dao.FinancialTransactionRepository;
import com.bench.project.bench.entity.FinancialTransactionsResponse;
import com.bench.project.bench.entity.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

@Service
public class FinancialTransactionServiceImpl implements FinancialTransactionService {

    private final FinancialTransactionRepository financialTransactionRepository;

    public FinancialTransactionServiceImpl(FinancialTransactionRepository financialTransactionRepository) {
        this.financialTransactionRepository = financialTransactionRepository;
    }

    @Override
    public Map<LocalDate, Double> getAllFinancialTransaction() throws InterruptedException, ExecutionException {
        List<ResponseEntity<FinancialTransactionsResponse>> transactions = financialTransactionRepository.fetchAllTransactions();
        Map<LocalDate, Double> totalAmountByTransactionDate = new TreeMap<>();
        this.createMap(transactions, totalAmountByTransactionDate);
        return totalAmountByTransactionDate;
    }

    private void createMap(List<ResponseEntity<FinancialTransactionsResponse>> transactions, Map<LocalDate, Double> totalAmountByTransactionDate) {
        List<Transaction> transactionsList = new ArrayList<>();
        for (ResponseEntity<FinancialTransactionsResponse> transactionFromResponse : transactions) {
            if (transactionFromResponse.getBody() != null) {
                transactionsList.addAll(transactionFromResponse.getBody().getTransactions());
            }
        }

        for (Transaction transaction : transactionsList) {
            double amount = totalAmountByTransactionDate.getOrDefault(transaction.getDate(), 0D);
            totalAmountByTransactionDate.put(transaction.getDate(), amount + transaction.getAmount());
        }
    }
}
