package com.finalOutput.bank.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/banking_db";
        String USERNAME = "root";
        String PASSWORD = "";

        try{
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch(SQLException e){
            System.out.println("[CONNECTION ERROR]" + e.getMessage());
            throw e;
        }

    }
}
