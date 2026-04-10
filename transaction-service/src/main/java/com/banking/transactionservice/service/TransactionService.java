package com.banking.transactionservice.service;

import com.banking.transactionservice.dto.TransactionEvent;
import com.banking.transactionservice.dto.TransactionRequest;
import com.banking.transactionservice.entity.Transaction;
import com.banking.transactionservice.enums.TransactionStatus;
import com.banking.transactionservice.enums.TransactionType;
import com.banking.transactionservice.kafka.TransactionEventProducer;
import com.banking.transactionservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionEventProducer producer;

    public Transaction createTransaction(TransactionRequest request) {
        Transaction transaction = Transaction.builder()
                .accountId(request.getAccountId())
                .type(request.getType())
                .amount(request.getAmount())
                .description(request.getDescription())
                .status(TransactionStatus.SUCCESS)
                .createdAt(LocalDateTime.now())
                .build();

        Transaction saved = transactionRepository.save(transaction);

        // PUBLISH TO KAFKA AFTER SAVE
        TransactionEvent event = TransactionEvent.builder()
                .transactionId(saved.getId())
                .accountId(saved.getAccountId())
                .type(saved.getType().name())
                .amount(saved.getAmount())
                .description(saved.getDescription())
                .timestamp(saved.getCreatedAt())
                .build();

        producer.publish(event);

        return saved;
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    public List<Transaction> getTransactionHistory(Long accountId) {
        return transactionRepository.findByAccountIdOrderByDate(accountId);
    }

    public List<Transaction> getTransactionsByType(Long accountId, TransactionType type) {
        return transactionRepository.findByAccountIdAndType(accountId, type);
    }
}
