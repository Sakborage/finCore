package com.example.finCore.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
public class Account {

    public enum AccountType {
        SAVINGS,
        CURRENT
    }

    public enum AccountStatus {
        ACTIVE,
        INACTIVE,
        CLOSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    private AccountStatus accountStatus;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Version
    private Long version;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }


}
