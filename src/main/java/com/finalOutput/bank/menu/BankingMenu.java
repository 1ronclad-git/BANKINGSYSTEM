package com.finalOutput.bank.menu;

import java.util.Scanner;

public class BankingMenu {
    Scanner scanner = new Scanner(System.in);

    public void start(){
        boolean running = true;

        while(running){
            printMenu();

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice){
//                case "1" -> createAccount();
                case "1" -> System.out.println("CREATE");
                case "2" -> System.out.println("BALANCE");
                case "3" -> System.out.println("LIST");
                case "4" -> System.out.println("DEPOSIT");
                case "5" -> System.out.println("WITHDRAW");
                case "6" -> System.out.println("TRANSFER");
                case "7" -> System.out.println("HISTORY");
                case "0" -> {
                    running = false;
                    System.out.println("Thank you for using Banking Management System. Goodbye!");
                }
                default -> System.out.println("[WARNING] Invalid choice '" + choice + "'. Try again.");
            }

        }
        scanner.close();
    }

    public void printMenu(){
        System.out.println();
        System.out.println("==================================");
        System.out.println("    BANKING MANAGEMENT SYSTEM     ");
        System.out.println("==================================");
        System.out.println(" 1. Create Account");
        System.out.println(" 2. Balance Inquiry");
        System.out.println(" 3. List Accounts");
        System.out.println(" 4. Deposit");
        System.out.println(" 5. Withdraw");
        System.out.println(" 6. Transfer");
        System.out.println(" 7. Transaction History");
        System.out.println(" 0. Exit");
        System.out.println("==================================");
    }
}
