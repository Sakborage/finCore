package com.example.finCore.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransactionDTO {

    @NotBlank
    private String accountNumber;

    @NotNull
    @Min(value = 0, message = "Amount must be greater than or equal to zero")
    private double amount;

    public TransactionDTO(String accountNumber,double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public @NotBlank String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(@NotBlank String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public @NotNull @Min(value = 0, message = "Amount must be greater than or equal to zero") double getAmount() {
        return amount;
    }

    public void setAmount(@NotNull @Min(value = 0, message = "Amount must be greater than or equal to zero") double amount) {
        this.amount = amount;
    }
}
