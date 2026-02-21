package com.example.finCore.controller;

import com.example.finCore.DTO.TransactionDTO;
import com.example.finCore.entity.Account;
import com.example.finCore.entity.Transaction;
import com.example.finCore.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

   @GetMapping("/{accountNumber}")
    public ResponseEntity<List<Transaction> >getHistory(@PathVariable  String accountNumber){
       List<Transaction> transactionHistory = transactionService.getTransactionHistory(accountNumber);
       return new ResponseEntity(transactionHistory, HttpStatus.FOUND);
    }

    @PostMapping("/deposit")
   public ResponseEntity<Account> DepositAmount( @Valid @RequestBody TransactionDTO transactionDTO){
       Account account = transactionService.Deposit(transactionDTO.getAccountNumber(), transactionDTO.getAmount());
       return new ResponseEntity<>(account,HttpStatus.OK);
   }


   @PostMapping("/withdraw")
   public ResponseEntity<Account> WithdrawAmount(@Valid @RequestBody TransactionDTO transactionDTO){
        String accountNumber = transactionDTO.getAccountNumber();
        double amount = transactionDTO.getAmount();
       Account account = transactionService.Withdraw(accountNumber, amount);
       return new ResponseEntity<>(account,HttpStatus.OK);
   }

   @PostMapping("/transfer")
   public ResponseEntity<Account> TransferAmount(String fromAccountNumber, String toAccountNumber, @RequestBody double amount){
       transactionService.transfer(fromAccountNumber, toAccountNumber, amount);
       return new ResponseEntity<>(HttpStatus.OK);
   }

}
