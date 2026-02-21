package com.example.finCore.repo;

import com.example.finCore.entity.Account;
import com.example.finCore.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    List<Transaction> findByFromAccountOrToAccount(Account account, Account account1);
}
