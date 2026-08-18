package com.finalOutput.bank.dao.impl;

import com.finalOutput.bank.config.DBConnection;
import com.finalOutput.bank.dao.TransactionDAO;
import com.finalOutput.bank.model.Transaction;
import com.finalOutput.bank.model.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    private static final String SQL_INSERT = "INSERT INTO transactions (account_number, transaction_type, amount, balance_after, reference_number ,remarks) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_BY_ACCOUNT = "SELECT * FROM transactions WHERE account_number = ? ORDER BY created_at DESC";

    @Override
    public void insertTransaction(Transaction transaction) throws SQLException {
        try (Connection connection = DBConnection.getConnection()) {
            insertTransactions(connection, transaction);
        } catch (SQLException e) {
            System.out.println("Failed to insert transaction" + e.getMessage());
            throw e;
        }
    }

    @Override
    public void insertTransactions(Connection connection, Transaction transaction) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, transaction.getAccountNumber());
            preparedStatement.setString(2, transaction.getTransactionType().name());
            preparedStatement.setBigDecimal(3, transaction.getAmount());
            preparedStatement.setBigDecimal(4, transaction.getBalanceAfter());
            preparedStatement.setString(5, transaction.getReferenceNumber());
            preparedStatement.setString(6, transaction.getRemarks());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaction.setTransactionId(generatedKeys.getLong(1));
                }
            }

        }
    }

    @Override
    public List<Transaction> findByAccountNumber(String accountNumber) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ACCOUNT)) {

            preparedStatement.setString(1, accountNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(mapRow(resultSet));
                }
            }

        }
        return transactions;
    }

//    -------------------------------------
//    PRIVATE HELPERS
//    -------------------------------------

    private Transaction mapRow(ResultSet resultSet) throws SQLException {
        Transaction txn = new Transaction();
        txn.setTransactionId(resultSet.getLong("transaction_id"));
        txn.setReferenceNumber(resultSet.getString("reference_number"));
        txn.setAccountNumber(resultSet.getString("account_number"));
        txn.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
        txn.setAmount(resultSet.getBigDecimal("amount"));
        txn.setBalanceAfter(resultSet.getBigDecimal("balance_after"));
        txn.setRemarks(resultSet.getString("remarks"));

        return txn;
    }
}
