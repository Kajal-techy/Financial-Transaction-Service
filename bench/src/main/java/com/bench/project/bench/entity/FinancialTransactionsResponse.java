package com.bench.project.bench.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinancialTransactionsResponse {

    int totalCount;
    int page;
    List<Transaction> transactions;
}
