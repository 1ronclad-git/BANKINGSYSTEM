package com.finalOutput.bank.service;

import com.finalOutput.bank.dao.AccountDAO;
import com.finalOutput.bank.model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AccountService {

    private final AccountDAO accountDAO;
    private final Scanner scanner;

    public AccountService(AccountDAO accountDAO, Scanner scanner) {
        this.accountDAO = accountDAO;
        this.scanner = scanner;
    }

    public void createAccount() {
        System.out.println("\n-------- CREATE ACCOUNT --------");

        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("[ERROR] Account holder name cannot be empty.");
            return;
        }

        BigDecimal initialDeposit = promptAmount("Enter initial deposit amount: ");
        if (initialDeposit == null) return;

        // unique account number generator
        String accountNumber = generateAccountNumber();

        Account account = new Account(accountNumber, name, initialDeposit);

        try {
            accountDAO.createAccount(account);
            System.out.println("\n[SUCCESS] Account created successfully!");
            printAccountSummary(account);
        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    public void balanceInquiry() {
        System.out.println("\n-------- BALANCE INQUIRY --------");

        System.out.print("Enter account holder number: ");
        String accountNumber = scanner.nextLine().trim();

        try {
            Account account = findAccount(accountNumber);
            System.out.println("\n[SUCCESS] Balance Inquiry");
            printAccountSummary(account);
        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    public void listAccounts() {
        System.out.println("\n-------- LIST ACCOUNTS --------");

        try {
            List<Account> accounts = accountDAO.getAllAccounts();
            if (accounts.isEmpty()) System.out.println("[ERROR] No accounts found.");
            printAccountsTable(accounts);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    ------------------------------------
//    PUBLIC HELPER
//    ------------------------------------
    public Account findAccount(String accountNumber) throws SQLException {
        Optional<Account> option = accountDAO.getByAccountNumber(accountNumber);
        return option.orElse(null);
    }

//    ------------------------------------
//    PRIVATE HELPERS
//    ------------------------------------

    private BigDecimal promptAmount(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            BigDecimal amount = new BigDecimal(input);
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("[ERROR] Amount cannot be negative.");
                return null;
            }
            return amount;
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid amount." + input);
            return null;
        }
    }

//    ------------------------------------
//    GENERATE UNIQUE ACCOUNT NUMBER
//    ------------------------------------

    private String generateAccountNumber() {
        long key = System.currentTimeMillis() % 1_000_000_000L;
        return String.format("BMS-%010d", key);
    }

//    ------------------------------------
//    DISPLAY INFO AFTER CREATION
//    ------------------------------------

    private void printAccountSummary(Account account) {
        System.out.println("Account Number : " + account.getAccountNumber());
        System.out.println("Account Name   : " + account.getAccountName());
        System.out.printf("Balance        : PHP %.2f%n", account.getBalance());
        if (account.getCreatedAt() != null) {
            System.out.println("Created at: " + account.getCreatedAt());
        }
    }

//    ------------------------------------
//    DISPLAY ALL ACCOUNTS TABLE
//    ------------------------------------

    private void printAccountsTable(List<Account> accounts) {
        String line = "+----+-----------------------+-------------------------+---------------+";
        System.out.println(line);
        System.out.printf("| %-2s | %-21s | %-23s | %-13s |%n",
                "No", "Account Number", "Account Name", "Balance (PHP)");
        System.out.println(line);

        int i = 1;
        for (Account account : accounts) {
            System.out.printf("| %-2s | %-21s | %-23s | %-13s |%n",
                    i++,
                    account.getAccountNumber(),
                    account.getAccountName(),
                    account.getBalance());
        }
        System.out.println(line);
        System.out.println("  Total accounts: " + accounts.size());
    }

}
