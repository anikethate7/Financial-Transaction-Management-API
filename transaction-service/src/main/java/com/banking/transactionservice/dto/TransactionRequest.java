package com.banking.transactionservice.dto;

import com.banking.transactionservice.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransactionRequest {

    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @Positive(message = "Amount must be positive")
    private Double amount;

    private String description;
}
