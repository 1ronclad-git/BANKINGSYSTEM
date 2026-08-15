package com.finalOutput.bank.dao;

import com.finalOutput.bank.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {
    void createAccount(Account account) throws SQLException;
    List<Account> getAllAccounts();
}
