package com.example.finCore.service;


import com.example.finCore.entity.Account;
import com.example.finCore.entity.Transaction;
import com.example.finCore.exception.NotFoundException;
import com.example.finCore.exception.TransactionLimitExceeded;
import com.example.finCore.repo.AccountRepo;
import com.example.finCore.repo.TransactionRepo;
import org.jboss.logging.BasicLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {


    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AccountRepo accountRepo;

    private final BasicLogger logger = org.jboss.logging.Logger.getLogger(TransactionService.class);


    @Transactional
    public Account Deposit(String accountNumber, double amount) {
        Account account = accountRepo.findByAccountNumber(accountNumber).orElseThrow(
                () -> new NotFoundException("Account not found with account number: " + accountNumber));


        if(account.getAccountStatus()!= Account.AccountStatus.ACTIVE){
            throw new IllegalStateException("Account is not active");
        }

        account.setBalance(account.getBalance()+amount);

        Transaction tsx=new Transaction();
        tsx.setReferenceId(generateTransactionRef());
        tsx.setAmount(BigDecimal.valueOf(amount));
        tsx.setTransactionType(Transaction.TransactionType.DEPOSIT);
        tsx.setToAccount(account);

        transactionRepo.save(tsx);

        logger.info("Transcation SuccESSful with reference id: "+tsx.getReferenceId());

        return accountRepo.save(account);

    }


    @Transactional
    public Account Withdraw(String accountNumber, double amount) {


        Account account = accountRepo.findByAccountNumber(accountNumber).orElseThrow(
                () -> new NotFoundException("Account not found with account number: " + accountNumber));

        if(account.getAccountStatus()!= Account.AccountStatus.ACTIVE){
            throw new IllegalStateException("Account is not active");
        }

        if(account.getBalance()<amount){
            throw new IllegalStateException("Insufficient balance");
        }
        Long dailyWithdrawnAmount = transactionRepo.getTodayOutgoingAmount(
                account.getAccountNumber(),startOfDay(),endOfDay());
        if(dailyWithdrawnAmount!=null && dailyWithdrawnAmount+amount>getDailyLimit(account)){
            throw new TransactionLimitExceeded("Daily withdrawal limit exceeded");
        }

        account.setBalance(account.getBalance()-amount);

        Transaction tsx=new Transaction();
        tsx.setReferenceId(generateTransactionRef());
        tsx.setAmount(BigDecimal.valueOf(amount));
        tsx.setTransactionType(Transaction.TransactionType.WITHDRAWAL);
        tsx.setFromAccount(account);

        transactionRepo.save(tsx);

        return accountRepo.save(account);

    }


    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = accountRepo.findByAccountNumber(fromAccountNumber).orElseThrow(
                () -> new NotFoundException("Source account not found with account number: " + fromAccountNumber));

        Account toAccount = accountRepo.findByAccountNumber(toAccountNumber).orElseThrow(
                () -> new NotFoundException("Destination account not found with account number: " + toAccountNumber));

        if(fromAccount.getAccountStatus()!= Account.AccountStatus.ACTIVE || toAccount.getAccountStatus()!= Account.AccountStatus.ACTIVE){
            throw new IllegalStateException("One or both accounts are not active");
        }

        if(fromAccount.getBalance()<amount){
            throw new IllegalStateException("Insufficient balance in source account");
        }

        Long dailyWithdrawnAmount = transactionRepo.getTodayOutgoingAmount(
                fromAccount.getAccountNumber(),startOfDay(),endOfDay());
        if(dailyWithdrawnAmount!=null && dailyWithdrawnAmount+amount>getDailyLimit(fromAccount)){
            throw new TransactionLimitExceeded("Daily withdrawal limit exceeded for source account");
        }

        fromAccount.setBalance(fromAccount.getBalance()-amount);
        toAccount.setBalance(toAccount.getBalance()+amount);

        Transaction tsx=new Transaction();
        tsx.setReferenceId(generateTransactionRef());
        tsx.setAmount(BigDecimal.valueOf(amount));
        tsx.setTransactionType(Transaction.TransactionType.TRANSFER);
        tsx.setFromAccount(fromAccount);
        tsx.setToAccount(toAccount);

        transactionRepo.save(tsx);

        accountRepo.save(fromAccount);
        accountRepo.save(toAccount);

    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        Account account = accountRepo.findByAccountNumber(accountNumber).orElseThrow(
                () -> new NotFoundException("Account not found with account number: " + accountNumber));

        return transactionRepo.findByFromAccountOrToAccount(account, account);
    }

    public String generateTransactionRef() {

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String randomPart = UUID.randomUUID()
                .toString()
                .substring(0, 6)
                .toUpperCase();

        return "FIN-" + timestamp + "-" + randomPart;
    }

    private long getDailyLimit(Account account){
        if(account.getAccountType()== Account.AccountType.SAVINGS){
            return 50000;
        }else if(account.getAccountType()== Account.AccountType.CURRENT) {
            return 500000;
        }

        return 0;

    }

    private Instant startOfDay(){
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        return startOfDay.atZone(java.time.ZoneId.systemDefault()).toInstant();
    }

    private Instant endOfDay(){
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return endOfDay.atZone(java.time.ZoneId.systemDefault()).toInstant();
    }
}
