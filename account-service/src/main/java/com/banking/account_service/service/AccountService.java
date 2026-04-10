package com.banking.account_service.service;

import com.banking.account_service.dto.AccountRequestDto;
import com.banking.account_service.entity.Account;
import com.banking.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    // ── Create account ───────────────────────────────────
    public Account createAccount(AccountRequestDto dto) {
        // generate a unique account number
        String accountNumber = "ACC" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .holderName(dto.getHolderName())
                .accountType(dto.getAccountType())
                .balance(dto.getInitialBalance())
                .status("ACTIVE")
                .build();

        Account saved = accountRepository.save(account);
        log.info("Account created: {}", saved.getAccountNumber());
        return saved;
    }

    // ── Get by ID ────────────────────────────────────────
    @Transactional(readOnly = true)
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    // ── Get all ──────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // ── Deposit ──────────────────────────────────────────
    public Account deposit(Long id, BigDecimal amount) {
        Account account = getAccountById(id);
        account.setBalance(account.getBalance().add(amount));
        Account updated = accountRepository.save(account);
        log.info("Deposited {} to account {}", amount, account.getAccountNumber());
        return updated;
    }

    // ── Withdraw ─────────────────────────────────────────
    public Account withdraw(Long id, BigDecimal amount) {
        Account account = getAccountById(id);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        Account updated = accountRepository.save(account);
        log.info("Withdrew {} from account {}", amount, account.getAccountNumber());
        return updated;
    }

    // ── Get balance ──────────────────────────────────────
    @Transactional(readOnly = true)
    public BigDecimal getBalance(Long id) {
        return getAccountById(id).getBalance();
    }
}