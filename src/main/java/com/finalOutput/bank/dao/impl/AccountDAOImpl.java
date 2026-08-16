package com.finalOutput.bank.dao.impl;

import com.finalOutput.bank.config.DBConnection;
import com.finalOutput.bank.dao.AccountDAO;
import com.finalOutput.bank.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO {

    private final static String SQL_INSERT = "INSERT INTO accounts(account_number, account_name, balance) VALUES (?, ?, ?)";
    private final static String SQL_FIND_ALL = "SELECT * FROM accounts ORDER BY created_at ASC";
    private final static String SQL_FIND_BY_NUMBER = "SELECT * FROM accounts WHERE account_number = ?";

    @Override
    public void createAccount(Account account) {
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);){

            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.setString(2, account.getAccountName());
            preparedStatement.setBigDecimal(3, account.getBalance());
            preparedStatement.execute();


        }catch(SQLException e){
            System.out.println("[ERROR]" + e.getMessage());
        }

    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();){

            while(resultSet.next()){
                Account account = new Account(resultSet.getString("account_number"), resultSet.getString("account_name"), resultSet.getBigDecimal("balance"));
                accounts.add(account);
            }

        }catch(SQLException e){
            System.out.println("[ERROR]" + e.getMessage());
        }

        return accounts;
    }

    @Override
    public Optional<Account> getByAccountNumber(String accountNumber) throws SQLException {
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_NUMBER)){

            preparedStatement.setString(1, accountNumber);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    return Optional.of(new Account(resultSet.getString("account_number"), resultSet.getString("account_name"), resultSet.getBigDecimal("balance")));
                }
                return Optional.empty();
            }
        }catch(SQLException e){
            System.out.println("[ERROR]" + e.getMessage());
            throw e;
        }
    }


}
