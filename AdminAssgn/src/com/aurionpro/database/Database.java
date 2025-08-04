package com.aurionpro.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // MySQL Version
    private static final Database INSTANCE = new Database();
    private Connection connection = null;

    private Database() {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university", "root", "Rahqan@123");
            System.out.println("Connection to MySQL database has been established successfully.");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (connection == null) {
            connect();
        }
        return connection;
    }


    // PostgreSQL Version (Commented out for now)
    /*
    private static Database instance;
    private Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/university";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private Database() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found.", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Database getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new Database();
        }
        return instance;
    }
    */
}