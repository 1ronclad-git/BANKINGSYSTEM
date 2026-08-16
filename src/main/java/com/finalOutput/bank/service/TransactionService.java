package com.finalOutput.bank.service;

import com.finalOutput.bank.dao.AccountDAO;
import com.finalOutput.bank.dao.TransactionDAO;
import com.finalOutput.bank.model.Account;
import com.finalOutput.bank.model.Transaction;
import com.finalOutput.bank.model.TransactionType;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransactionService {
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;
    private final AccountService accountService;
    private final Scanner scanner;

    public TransactionService(AccountDAO accountDAO, TransactionDAO transactionDAO, AccountService accountService, Scanner scanner) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
        this.accountService = accountService;
        this.scanner = scanner;
    }

    public void deposit(){
        System.out.println("\n=============== DEPOSIT =============");

        try{
            Account account = promptAccount();
            BigDecimal amount = promptPositiveAmount("Enter amount to deposit: ");

            BigDecimal newBalance = account.getBalance().add(amount);
            accountDAO.updateBalance(account.getAccountNumber(), newBalance);

            Transaction txn  = new Transaction(
                    ReferenceNumberGenerator(),
                    account.getAccountNumber(),
                    TransactionType.DEPOSIT,
                    amount,
                    newBalance,
                    "Cash Deposit"
            );
            transactionDAO.insertTransaction(txn);

            System.out.println("[SUCCESS] Deposit Successful.");

        }catch(Exception e){}
    }

//    ----------------------------
//    PRIVATE HELPERS
//    ----------------------------

    private Account promptAccount() throws Exception{
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();
        return accountService.findAccount(accountNumber);
    }

    private BigDecimal promptPositiveAmount(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try{
            BigDecimal amount = new BigDecimal(input);
            if(amount.compareTo(BigDecimal.ZERO) < 0){
                System.out.println("Amount must be greater than zero");
            }
            return amount;
        }catch(Exception e){
            System.out.println("[ERROR]" + e.getMessage());
            return null;
        }
    }

    private String ReferenceNumberGenerator(){
        final String PREFIX = "TXN";
        long key = System.currentTimeMillis() % 1_000_000_000L;
        return String.format(PREFIX + "-%09d", key);
    }

    private printTransactionInfo(Transaction txn, String accountName) {
        String line = "====================================";
        System.out.print(line);
        System.out.println();
    }
}
