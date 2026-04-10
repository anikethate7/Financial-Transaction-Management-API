package com.banking.account_service.dto;

import com.banking.account_service.entity.Account;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountRequestDto {

    @NotBlank(message = "Holder name is required")
    private String holderName;

    @NotNull(message = "Account type is required")
    private Account.AccountType accountType;

    @NotNull(message = "Initial balance is required")
    @DecimalMin(value = "0.0", message = "Balance must be non-negative")
    private BigDecimal initialBalance;
}