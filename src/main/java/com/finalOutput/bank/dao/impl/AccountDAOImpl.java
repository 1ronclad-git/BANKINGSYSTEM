package com.finalOutput.bank.dao.impl;

import com.finalOutput.bank.config.DBConnection;
import com.finalOutput.bank.dao.AccountDAO;
import com.finalOutput.bank.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    private final static String INSERT_ACCOUNT = "INSERT INTO accounts(account_number, account_name, balance) VALUES (?, ?, ?)";
    private final static String GET_ALL_ACCOUNT = "SELECT account_number, account_name, balance FROM accounts ORDER BY created_at ASC";

    @Override
    public boolean createAccount(Account account) {
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT);){

            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.setString(2, account.getAccountName());
            preparedStatement.setBigDecimal(3, account.getBalance());
            preparedStatement.execute();


        }catch(SQLException e){
            System.out.println("[ERROR]" + e.getMessage());
        }

        return false;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ACCOUNT);
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
}
