package com.finalOutput.bank.dao.impl;

import com.finalOutput.bank.dao.AccountDAO;
import com.finalOutput.bank.model.Account;

import java.util.List;

public class AccountDAOImpl implements AccountDAO {
    private final static String SQL_INSERT = "INSERT INTO accounts(first_name, last_name, email) VALUES(?, ?, ?)";
    private final static String SQL_INSERT = "";
    private final static String SQL_INSERT = "";

    @Override
    public boolean createAccount(Account account) {
        return false;
    }

    @Override
    public boolean Account(Account account) {
        return false;
    }

    @Override
    public List<Account> listAccounts() {
        return List.of();
    }
}
