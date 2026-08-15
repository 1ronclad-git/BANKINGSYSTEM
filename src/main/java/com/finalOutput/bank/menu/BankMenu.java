package com.finalOutput.bank.menu;

import com.finalOutput.bank.dao.AccountDAO;
import com.finalOutput.bank.dao.impl.AccountDAOImpl;
import com.finalOutput.bank.service.AccountService;

import java.util.Scanner;

public class BankMenu {

    private final AccountService accountService;
    private final Scanner scanner;

    public BankMenu() {
        scanner = new Scanner(System.in);

        AccountDAO accountDAO = new AccountDAOImpl();
        accountService = new AccountService(accountDAO, scanner);
    }

    public void start() {
        boolean running = true;

        while (running) {
            printMenu();

            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> accountService.createAccount();
                case "0" -> {
                    System.out.println("Thank you for using our Bank. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice '" + input + "' please enter 1 to 7, 0 to Exit.");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n====================================");
        System.out.println("         BANKING APPLICATION");
        System.out.println("====================================");
        System.out.println("1. Create Account");
        System.out.println("2. Balance Inquiry");
        System.out.println("3. List Accounts");
        System.out.println("4. Deposit");
        System.out.println("5. Withdraw");
        System.out.println("6. Transfer");
        System.out.println("7. Transaction History");
        System.out.println("0. Exit");
        System.out.println("====================================");
    }
}