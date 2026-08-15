package com.finalOutput.bank.app;

import com.finalOutput.bank.menu.BankMenu;

public class Main {
    static void main(String[] args) {

        try{
            BankMenu bankMenu = new BankMenu();
            bankMenu.start();
        }catch(Exception e){
            System.out.println("[ERROR]" + e.getMessage());
            System.exit(1);
        }
    }
}