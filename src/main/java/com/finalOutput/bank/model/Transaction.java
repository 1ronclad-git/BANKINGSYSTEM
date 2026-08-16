package com.finalOutput.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private long transactionId;
    private String referenceNumber;
    private String accountNumber;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private String referenceAccount;
    private LocalDateTime createdAt;

    public Transaction() {
    }

    //    ------------------------------
    //    CONSTRUCTOR
    //    ------------------------------

    public Transaction(String referenceNumber, String accountNumber, TransactionType transactionType, BigDecimal amount, BigDecimal balanceAfter, String referenceAccount) {
        this.referenceNumber = referenceNumber;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.referenceAccount = referenceAccount;
    }

    //    ------------------------------
    //    GETTER AND SETTER
    //    ------------------------------


    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getReferenceAccount() {
        return referenceAccount;
    }

    public void setReferenceAccount(String referenceAccount) {
        this.referenceAccount = referenceAccount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
