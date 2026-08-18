package com.finalOutput.bank.dao;

import com.finalOutput.bank.model.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TransactionDAO {
    void insertTransaction(Transaction transaction) throws SQLException;

    void insertTransactions(Connection connection, Transaction transaction) throws SQLException;

    List<Transaction> findByAccountNumber(String accountNumber) throws SQLException;

}
