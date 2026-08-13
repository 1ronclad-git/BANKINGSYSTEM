package com.finalOutput.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {
    private long accountId;
    private String accountName;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    -------------------------------------------------------
//    CONSTRUCTOR
//    -------------------------------------------------------

    public Account() {}

    public Account(long accountId, String accountName, BigDecimal balance) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
    }

//    -------------------------------------------------------
//    GETTER AND SETTER
//    -------------------------------------------------------

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}



