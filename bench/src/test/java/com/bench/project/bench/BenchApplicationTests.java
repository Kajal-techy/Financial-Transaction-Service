package com.bench.project.bench;

import com.bench.project.bench.dao.FinancialTransactionRepository;
import com.bench.project.bench.entity.FinancialTransactionsResponse;
import com.bench.project.bench.entity.Transaction;
import com.bench.project.bench.service.FinancialTransactionService;
import com.bench.project.bench.service.FinancialTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
class BenchApplicationTests {

    private FinancialTransactionService financialTransactionService;

    private List<ResponseEntity<FinancialTransactionsResponse>> response;

    @Mock
    private FinancialTransactionRepository financialTransactionRepository;

    @BeforeEach
    void setUp() {
        this.financialTransactionService = new FinancialTransactionServiceImpl(financialTransactionRepository);
        response = List.of(ResponseEntity.ok()
                .body(FinancialTransactionsResponse.builder()
                        .page(1)
                        .totalCount(10)
                        .transactions(List.of(Transaction.builder()
                                .amount(100)
                                .date(LocalDate.of(2013, 12, 12))
                                .company("ABC")
                                .ledger("TEST").build(), Transaction.builder()
                                .amount(100)
                                .date(LocalDate.of(2013, 12, 12))
                                .company("ABC")
                                .ledger("TEST").build())).build()));

    }

    @Test
    void testGetAllFinancialTransaction() throws ExecutionException, InterruptedException {
        when(financialTransactionRepository.fetchAllTransactions()).thenReturn(response);
        Map<LocalDate, Double> financialTransactions = financialTransactionService.getAllFinancialTransaction();
        assertThat(financialTransactions).isEqualTo(Map.of(LocalDate.of(2013, 12, 12), 200.0));
    }

    @Test
    void testIfHasNoContent() throws ExecutionException, InterruptedException {
        when(financialTransactionRepository.fetchAllTransactions()).thenReturn(List.of());
        Map<LocalDate, Double> financialTransactions = financialTransactionService.getAllFinancialTransaction();
        assertThat(financialTransactions).isEqualTo(Map.of());
    }

}
