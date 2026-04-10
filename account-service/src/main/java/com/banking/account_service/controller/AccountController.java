package com.banking.account_service.controller;

import com.banking.account_service.dto.AccountRequestDto;
import com.banking.account_service.dto.AmountRequestDto;
import com.banking.account_service.entity.Account;
import com.banking.account_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // POST /api/accounts
    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountRequestDto dto) {
        Account account = accountService.createAccount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    // GET /api/accounts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    // GET /api/accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // POST /api/accounts/{id}/deposit
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long id,
                                           @Valid @RequestBody AmountRequestDto dto) {
        return ResponseEntity.ok(accountService.deposit(id, dto.getAmount()));
    }

    // POST /api/accounts/{id}/withdraw
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long id,
                                            @Valid @RequestBody AmountRequestDto dto) {
        return ResponseEntity.ok(accountService.withdraw(id, dto.getAmount()));
    }

    // GET /api/accounts/{id}/balance
    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getBalance(id));
    }
}