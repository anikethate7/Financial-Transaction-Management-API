package com.banking.transactionservice.entity;

// src/main/java/com/banking/transactionservice/entity/Notification.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
