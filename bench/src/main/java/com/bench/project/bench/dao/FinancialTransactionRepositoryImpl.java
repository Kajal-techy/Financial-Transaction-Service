package com.bench.project.bench.dao;

import com.bench.project.bench.entity.FinancialTransactionsResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Repository
public class FinancialTransactionRepositoryImpl implements FinancialTransactionRepository {

    private final RestTemplate restTemplate;
    private final HttpEntity<String> entity;

    public static final int TOTAL_RECORDS_PER_PAGE = 10;

    public FinancialTransactionRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<>(headers);
    }

    @Override
    public List<ResponseEntity<FinancialTransactionsResponse>> fetchAllTransactions() throws InterruptedException, ExecutionException {

        // Fetch the first page to get the total pages of in the transaction book
        ResponseEntity<FinancialTransactionsResponse> financialTransactionsResponseForPageOne = getFinancialTransactions(1);
        int totalPages = 0;
        if (financialTransactionsResponseForPageOne.getStatusCode() == HttpStatus.OK) {
            totalPages = (int) Math.floor(financialTransactionsResponseForPageOne.getBody().getTotalCount() / (double) TOTAL_RECORDS_PER_PAGE);
        }


        // Fetch rest of the pages (n-1) in parallel
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        List<ResponseEntity<FinancialTransactionsResponse>> financialTransactionsResponseList = new ArrayList<>();
        Future futureFinancialTransaction = null;
        int i;
        for (i = 1; i <= totalPages; i++) {
            int pageNo = i;
            Callable financialTransactionTask = (() -> {
                ResponseEntity<FinancialTransactionsResponse> financialTransactionsResponse = getFinancialTransactions(pageNo);
                financialTransactionsResponseList.add(financialTransactionsResponse);
                return financialTransactionsResponse;
            });
            futureFinancialTransaction = executor.submit(financialTransactionTask);
        }
        if (futureFinancialTransaction != null) {
            futureFinancialTransaction.get();
        }
        executor.shutdown();

        return financialTransactionsResponseList;
    }

    private ResponseEntity<FinancialTransactionsResponse> getFinancialTransactions(int pageNo) {
        ResponseEntity<FinancialTransactionsResponse> response = restTemplate
                .exchange("https://resttest.bench.co/transactions/" + pageNo + ".json", HttpMethod.GET, entity, FinancialTransactionsResponse.class);
        if (response.getBody() == null) {
            // TODO: Throw custom error instead
            throw new RuntimeException("Response body is not present");
        }
        return response;
    }
}
