package com.finalOutput.bank.dao;

import com.finalOutput.bank.model.Account;

import java.util.List;

public interface AccountDAO {
    boolean createAccount(Account account);
    boolean Account(Account account);
    List<Account> listAccounts();
}
