package com.finalOutput.bank.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String PROP_URL = "db.url";
    private static final String PROP_USERNAME = "db.username";
    private static final String PROP_PASSWORD = "db.password";

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {

        try {
            Connection connection = DriverManager.getConnection(PROP_URL, PROP_USERNAME, PROP_PASSWORD);
            System.out.println("Connection successful");
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection Failed" + e.getMessage());
            throw e;
        }
    }
}
