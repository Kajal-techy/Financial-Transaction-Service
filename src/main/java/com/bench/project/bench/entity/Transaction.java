package com.bench.project.bench.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @JsonProperty("Date")
    LocalDate date;

    @JsonProperty("Ledger")
    String ledger;

    @JsonProperty("Amount")
    double amount;

    @JsonProperty("Company")
    String company;
}
