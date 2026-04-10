package com.banking.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent {

    private Long transactionId;
    private Long accountId;
    private String type;       // "DEBIT" or "CREDIT"
    private Double amount;
    private String description;
    private LocalDateTime timestamp;
}