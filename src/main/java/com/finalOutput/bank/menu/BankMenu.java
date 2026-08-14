package com.finalOutput.bank.menu;

import java.util.Scanner;

public class BankMenu {
    Scanner scanner = new Scanner(System.in);

    public void start(){
        boolean running = true;

        while(running){
            printMenu();

            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();

            switch (input){
                case "1" -> System.out.println("to Create Account");
                case "2" -> System.out.println("to See Account Balance");
                case "3" -> System.out.println("to Display List of Accounts");
                case "4" -> System.out.println("to Deposit on Account");
                case "5" -> System.out.println("to Withdraw on Account");
                case "6" -> System.out.println("to Transfer onto another Account");
                case "7" -> System.out.println("to see Transaction History");
                case "0" -> {
                    System.out.println("Thank you for using our Bank. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice '" + input + "' please enter 1 to 7, 0 to Exit.");
            }
        }
        scanner.close();
    }

    private void printMenu(){
        System.out.println("====================================");
        System.out.println("BANKING APPLICATION");
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