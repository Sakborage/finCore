package com.example.finCore.controller;

import com.example.finCore.DTO.AccountRequestDTO;
import com.example.finCore.entity.Account;
import com.example.finCore.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccounts(@PathVariable Long accountId){
        Account account = accountService.getAccount(accountId);

        return new ResponseEntity<>(account, HttpStatus.FOUND);
    }


    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable String accountNumber){
        Account account = accountService.getAccountByAccountNumber(accountNumber);

        return new ResponseEntity<>(account, HttpStatus.FOUND);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getUserAccounts(@PathVariable Long customerId){
        List<Account> accounts = accountService.getUserAccounts(customerId);
        return new ResponseEntity<>(accounts, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Account> addAccount(@Valid @RequestBody AccountRequestDTO accountRequestDTO){
        Account account = accountService.createAccount(accountRequestDTO);
        return new ResponseEntity<>(account,HttpStatus.CREATED);
    }

    @PutMapping("/{accountId}/close")
    public ResponseEntity<Account> closeAccount(
            @PathVariable Long accountId) {

        Account account = accountService.closeAccount(accountId);

        return ResponseEntity.ok(account);
    }

    @PutMapping("/{accountId}/inactive")
    public ResponseEntity<Account> freezeAccount(
            @PathVariable Long accountId) {

        Account account = accountService.freezeAccount(accountId);

        return ResponseEntity.ok(account);
    }

    @PutMapping("/{accountId}/activate")
    public ResponseEntity<Account> activateAccount(
            @PathVariable Long accountId) {

        Account account = accountService.activateAccount(accountId);

        return ResponseEntity.ok(account);
    }

}
