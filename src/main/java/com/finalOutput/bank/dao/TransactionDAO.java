package com.finalOutput.bank.dao;

import com.finalOutput.bank.model.Transaction;

import java.util.List;

public interface TransactionDAO {
    void insertTransaction(Transaction transaction);
}
