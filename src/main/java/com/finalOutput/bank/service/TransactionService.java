package com.finalOutput.bank.service;

import com.finalOutput.bank.config.DBConnection;
import com.finalOutput.bank.dao.AccountDAO;
import com.finalOutput.bank.dao.TransactionDAO;
import com.finalOutput.bank.model.Account;
import com.finalOutput.bank.model.Transaction;
import com.finalOutput.bank.model.TransactionType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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

//    ----------------------------
//    DEPOSIT
//    ----------------------------


    public void deposit() {
        System.out.println("\n-------- DEPOSIT --------");

        try {
            Account account = promptAccount();
            BigDecimal amount = promptPositiveAmount("Enter amount to deposit: ");

            BigDecimal prevBalance = account.getBalance();
            BigDecimal newBalance = account.getBalance().add(amount);
            accountDAO.updateBalance(account.getAccountNumber(), newBalance);

            Transaction txn = new Transaction(
                    referenceNumberGenerator(),
                    account.getAccountNumber(),
                    TransactionType.DEPOSIT,
                    amount,
                    newBalance,
                    "Cash Deposit"
            );
            transactionDAO.insertTransaction(txn);

            String mode = "Deposit";

            printTransactionInfo(txn, prevBalance, mode);
            System.out.println("\n[SUCCESS] Deposit Successful.");


        } catch (Exception e) {
            System.out.println("\n[ERROR] Deposit Failed. " + e.getMessage());
        }
    }

//    ----------------------------
//    WITHDRAW
//    ----------------------------

    public void withdraw() {
        System.out.println("\n-------- WITHDRAWAL --------");

        try {
            Account account = promptAccount();
            BigDecimal amount = promptPositiveAmount("Enter amount to withdraw: ");

            checkSufficientFunds(account, amount);

            BigDecimal prevBalance = account.getBalance();
            BigDecimal newBalance = account.getBalance().subtract(amount);
            accountDAO.updateBalance(account.getAccountNumber(), newBalance);

            Transaction txn = new Transaction(
                    referenceNumberGenerator(),
                    account.getAccountNumber(),
                    TransactionType.WITHDRAW,
                    amount,
                    newBalance,
                    "Cash Withdrawal"
            );
            transactionDAO.insertTransaction(txn);

            String mode = "Withdraw";

            printTransactionInfo(txn, prevBalance, mode);
            System.out.println("\n[SUCCESS] Withdrawal Successful.");


        } catch (Exception e) {
            System.out.println("\n[ERROR] Withdrawal Failed. " + e.getMessage());
        }
    }

//    ----------------------------
//    FUND TRANSFER
//    ----------------------------

    public void transfer() {
        System.out.println("\n-------- FUND TRANSFER --------");

        String senderNumber = null;
        String receiverNumber = null;
        BigDecimal amount = null;
        Account sender = null;
        Account receiver = null;

        // 1 Validation

        try {
            System.out.print("Enter sender account number: ");
            senderNumber = scanner.nextLine().trim();

            System.out.print("Enter receiver account number: ");
            receiverNumber = scanner.nextLine().trim();

            if (senderNumber.equalsIgnoreCase(receiverNumber)) {
                System.out.println("Sender and receiver account numbers are the same.");
            }

            amount = promptPositiveAmount("Enter amount to transfer: ");

            // Verification if both account exist.

            sender = accountService.findAccount(senderNumber);
            receiver = accountService.findAccount(receiverNumber);

            checkSufficientFunds(sender, amount);

        } catch (SQLException e) {
            System.out.println("\n[ERROR] Transfer Failed. " + e.getMessage());
        }

        // 2 Execution

        assert sender != null;
        BigDecimal senderNewBalance = sender.getBalance().subtract(amount);
        assert receiver != null;
        BigDecimal receiverNewBalance = receiver.getBalance().add(amount);

        // 3 Reference number for this transaction

        String sameRefNumber = referenceNumberGenerator();

        Transaction txnOut = new Transaction(
                sameRefNumber,
                senderNumber,
                TransactionType.TRANSFER_OUT,
                amount,
                senderNewBalance,
                "Transfer to " + receiverNumber + " (" + receiver.getAccountName() + ") "
        );

        Transaction txnIn = new Transaction(
                referenceNumberGenerator(),
                receiverNumber,
                TransactionType.TRANSFER_IN,
                amount,
                receiverNewBalance,
                "Transfer from " + senderNumber + " (" + sender.getAccountName() + ") "
        );

        // Connection

        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                // sender
                accountDAO.updateBalance(connection, senderNumber, senderNewBalance);

                // receiver
                accountDAO.updateBalance(connection, receiverNumber, receiverNewBalance);

                // transfer out
                transactionDAO.insertTransactions(connection, txnOut);

                // transfer in
                transactionDAO.insertTransactions(connection, txnIn);

                connection.commit();

                printTransferInfo(txnOut, senderNumber, receiverNumber);
                System.out.println("\n[SUCCESS] Transfer sent.");

            } catch (Exception e) {
                try {
                    connection.rollback();
                    System.out.println("\n[FAILED] Transfer rolled back for " + sameRefNumber);
                } catch (SQLException RollbackX) {
                    System.out.println("\n[FAILED] Rollback also failed" + RollbackX.getMessage());
                }
                System.out.println("Transfer and rollback for " + sameRefNumber);
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ignore) {
                }
            }
        } catch (SQLException e) {
            System.out.println("\n[ERROR] Could not connect to the database. " + e.getMessage());
        }
    }

//    ----------------------------
//    TRANSACTION HISTORY
//    ----------------------------

    public void transactionHistory() {
        System.out.println("\n-------- TRANSACTION HISTORY --------");

        try {
            System.out.print("Enter account number: ");
            String accountNumber = scanner.nextLine().trim();

            accountService.findAccount(accountNumber);

            List<Transaction> transactions = transactionDAO.findByAccountNumber(accountNumber);

            if (transactions.isEmpty()) {
                System.out.println("\nNo transactions found for account ." + accountNumber);
            }

            System.out.println("\nTransactions found for account " + accountNumber);
            printTransactionHistory(transactions);

        } catch (SQLException e) {
            System.out.println("\n[ERROR] " + e.getMessage());
        }

    }

//    ----------------------------
//    PRIVATE HELPERS
//    ----------------------------

    private Account promptAccount() throws Exception {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();
        return accountService.findAccount(accountNumber);
    }

    private BigDecimal promptPositiveAmount(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            BigDecimal amount = new BigDecimal(input);
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Amount must be greater than zero");
            }
            return amount;
        } catch (Exception e) {
            System.out.println("[ERROR]" + e.getMessage());
            return null;
        }
    }

    private void checkSufficientFunds(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient funds" + account.getBalance());
        }
    }

    private String referenceNumberGenerator() {
        final String PREFIX = "TXN";
        long key = System.currentTimeMillis() % 1_000_000_000L;
        return String.format(PREFIX + "%015d", key);
    }

    private void printTransactionInfo(Transaction txn, BigDecimal prevBalance, String mode) {
        String line = "=====================================";
        System.out.println(line);
        System.out.println("Previous Balance: " + prevBalance);
        System.out.println(mode + " Amount: " + txn.getAmount());
        System.out.println("New Balance: " + txn.getBalanceAfter());
    }

    private void printTransferInfo(Transaction txnOut, String senderNumber, String receiverNumber) {
        String line = "=====================================";
        System.out.println(line);
        System.out.println("From Account: " + senderNumber);
        System.out.println("To Account  : " + receiverNumber);
        System.out.println("Amount      : " + txnOut.getAmount());
    }

    private void printTransactionHistory(List<Transaction> transactions) {
        String line = "+----+---------------------+--------------+--------------+---------------+";
        System.out.println(line);
        System.out.printf("| %-2s | %-19s | %-12s | %-12s | %-12s |%n",
                "No", "Reference", "Type", "Amount", "Balance After");
        System.out.println(line);
        //String, String,

        int i = 1;
        for (Transaction txn : transactions) {
            System.out.printf("| %-2d | %-19s | %-12s | %12.2f | %12.2f  |%n",
                    i++,
                    txn.getReferenceNumber().length() > 19
                            ? txn.getReferenceNumber().substring(0, 19) : txn.getReferenceNumber(),
                    txn.getTransactionType().formatName(),
                    txn.getAmount(),
                    txn.getBalanceAfter()
                    );
        }
    }
}
