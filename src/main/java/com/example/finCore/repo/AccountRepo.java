package com.example.finCore.repo;

import com.example.finCore.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountRepo extends JpaRepository<Account,Long> {

    Optional<Account> findByAccountNumber(String accountNumber);
}
