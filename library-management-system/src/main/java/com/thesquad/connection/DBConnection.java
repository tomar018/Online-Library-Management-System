package com.thesquad.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String DB_URL;
    private String DB_USER;
    private String DB_PASSWORD;
    private Connection connection = null;

    @SuppressWarnings("UseSpecificCatch")
    public DBConnection() {
        try {
            // Load properties from application.properties
            Properties properties = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");

            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }

            properties.load(input);

            // Retrieve database properties
            DB_URL = properties.getProperty("db.url");
            DB_USER = properties.getProperty("db.user");
            DB_PASSWORD = properties.getProperty("db.password");

            // Load the MySQL JDBC driver
            Class.forName(JDBC_DRIVER);

            // Establish the connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection to MySQL database established successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error establishing connection with the database!");
        } catch (Exception ex) {
            System.out.println("Error establishing connection with the database!");
        }
    }

    // Getter for connection
    public Connection getConnection() {
        return connection;
    }

    // Close the MySQL connection
    @SuppressWarnings("CallToPrintStackTrace")
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection to MySQL database closed successfully.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
