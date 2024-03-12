package com.example.gestionbudget.db;

import org.sqlite.JDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

// import static com.example.financemanager.FinanceTrackerApplication.findAndCreateOSFolder;

public class Database {

    private static String dbPath = "";

    public static void setDbPath(String newPath) {
        dbPath = newPath;
    }

   // boolean dbInitialized = com.example.gestionbudget.db.Database.isOK(applicationDir + File.separator + "database.db");


    public static boolean isOK(String dbPath) {
        if (!checkDrivers()) return false;

        if (!checkConnection(dbPath)) return false;

        return createTableIfNotExists(dbPath);
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            return false;
        }
    }

    private static boolean checkConnection(String dbPath) {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            return false;
        }
    }

    private static boolean createTableIfNotExists(String dbPath) {
        String createTables =
                "CREATE TABLE IF NOT EXISTS expense(" +
                        "date TEXT NOT NULL," +
                        "housing REAL NOT NULL," +
                        "TOTAL REAL NOT NULL," +
                        "food REAL NOT NULL," +
                        "goingOut REAL NOT NULL," +
                        "transportation REAL NOT NULL," +
                        "travel REAL NOT NULL," +
                        "tax REAL NOT NULL," +
                        "other REAL NOT NULL" +
                        ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(createTables);
            statement.executeUpdate();
            System.out.println("Table created");
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    protected static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + dbPath);
        } catch (SQLException exception) {
            return null;
        }
        return connection;
    }
}