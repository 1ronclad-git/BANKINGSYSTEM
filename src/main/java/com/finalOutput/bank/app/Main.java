package com.finalOutput.bank.app;

import com.finalOutput.bank.menu.BankingMenu;

public class Main {
    static void main(String[] args) {
        try{
            BankingMenu bankingMenu = new BankingMenu();
            bankingMenu.start();
        } catch(Exception e){
            System.out.println("[FATAL] " + e.getMessage());
            System.exit(1);
        }

    }
}
