package com.example.finCore.repo;

import com.example.finCore.entity.Account;
import com.example.finCore.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    List<Transaction> findByFromAccountOrToAccount(Account account, Account account1);

    @Query("""
SELECT COALESCE(SUM(t.amount), 0)
FROM Transaction t
WHERE t.fromAccount.accountNumber = :accountNumber
AND t.transactionType IN ('WITHDRAW', 'TRANSFER')
AND t.createdAt BETWEEN :start AND :end
""")
    Long getTodayOutgoingAmount(
            @Param("accountNumber") String accountNumber,
            @Param("start") Instant start,
            @Param("end") Instant end);
}
