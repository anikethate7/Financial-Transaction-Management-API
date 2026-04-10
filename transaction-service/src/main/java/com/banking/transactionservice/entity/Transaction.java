package com.banking.transactionservice.entity;

// src/main/java/com/banking/transactionservice/entity/Transaction.java

import com.banking.transactionservice.enums.TransactionStatus;
import com.banking.transactionservice.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;   // DEBIT or CREDIT

    private Double amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;  // SUCCESS or FAILED

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<Notification> notifications;
}