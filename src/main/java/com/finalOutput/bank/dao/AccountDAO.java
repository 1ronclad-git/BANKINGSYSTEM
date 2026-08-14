package com.finalOutput.bank.dao;

import com.finalOutput.bank.model.Account;

import java.util.List;

public interface AccountDAO {
    boolean createAccount(Account account);
    List<Account> getAllAccounts();
}
