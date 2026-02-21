package com.example.finCore.service;

import com.example.finCore.DTO.AccountRequestDTO;
import com.example.finCore.entity.Account;
import com.example.finCore.exception.NotFoundException;
import com.example.finCore.repo.AccountRepo;
import com.example.finCore.repo.CustomerRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private CustomerRepo customerRepo;


    public Account getAccount(Long id) {
        return accountRepo.findById(id).orElseThrow(()-> new NotFoundException("Account not found"));
    }

    @Transactional
    public Account createAccount(@Valid AccountRequestDTO accountRequestDTO) {

        customerRepo.findById(accountRequestDTO.getCustomerId()).orElseThrow(()-> new NotFoundException("Customer not found"));

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(accountRequestDTO.getInitialDeposit());
        account.setCustomer(customerRepo.findById(accountRequestDTO.getCustomerId()).get());
        account.setAccountStatus(Account.AccountStatus.ACTIVE);
        account.setAccountType(accountRequestDTO.getAccountType());

        return accountRepo.save(account);


    }

    private String generateAccountNumber() {

        String bankCode = "620";   // FinCore internal code
        String randomPart = String.valueOf(
                ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L)
        );

        return bankCode + randomPart;
    }

    public Account getAccountByAccountNumber(String accountNumber) {
      return accountRepo.findByAccountNumber(accountNumber).
              orElseThrow(()-> new NotFoundException("Account not found with account number: "+accountNumber));

    }

    public List<Account> getUserAccounts(Long customerId) {
        customerRepo.findById(customerId).orElseThrow(()-> new NotFoundException("Customer not found"));
        return accountRepo.findAll().stream().filter(account -> account.getCustomer().getId().equals(customerId)).toList();
    }

    @Transactional
    public Account closeAccount(Long accountId) {
        Account account = accountRepo.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));
        account.setAccountStatus(Account.AccountStatus.CLOSED);
        return account;
    }

    @Transactional
    public Account freezeAccount(Long accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        account.setAccountStatus(Account.AccountStatus.INACTIVE);
       return account;
    }

    @Transactional
    public Account activateAccount(Long accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        account.setAccountStatus(Account.AccountStatus.ACTIVE);

        return account;
    }
}
