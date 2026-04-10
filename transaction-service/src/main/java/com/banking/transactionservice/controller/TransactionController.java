package com.banking.transactionservice.controller;

import com.banking.transactionservice.dto.TransactionRequest;
import com.banking.transactionservice.entity.Transaction;
import com.banking.transactionservice.enums.TransactionType;
import com.banking.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.createTransaction(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactionHistory(accountId));
    }

    @GetMapping("/account/{accountId}/type/{type}")
    public ResponseEntity<List<Transaction>> getByType(
            @PathVariable Long accountId,
            @PathVariable TransactionType type) {
        return ResponseEntity.ok(transactionService.getTransactionsByType(accountId, type));
    }
}
