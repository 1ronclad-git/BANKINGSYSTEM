package com.finalOutput.bank.dao;

import com.finalOutput.bank.model.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AccountDAO {
    void createAccount(Account account) throws SQLException;
    Optional<Account> getByAccountNumber(String accountNumber) throws SQLException;
    List<Account> getAllAccounts() throws SQLException;
}
