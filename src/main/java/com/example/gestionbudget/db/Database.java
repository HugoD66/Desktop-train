package com.example.gestionbudget.db;

import org.sqlite.JDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

// import static com.example.financemanager.FinanceTrackerApplication.findAndCreateOSFolder;

public class Database {
    private static final String location = "database.db";

    public static boolean isOK() {
        if (!checkDrivers()) return false;

        if (!checkConnection()) return false;

        return createTableIfNotExists();
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

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            return false;
        }
    }

    private static boolean createTableIfNotExists() {
        String createTables =
                    //                DROP TABLE IF EXISTS expense;
                """
                
                CREATE TABLE IF NOT EXISTS expense(
                        date TEXT NOT NULL,
                        housing REAL NOT NULL,
                        TOTAL REAL NOT NULL,
                        food REAL NOT NULL,
                        goingOut REAL NOT NULL,
                        transportation REAL NOT NULL,
                        travel REAL NOT NULL,
                        tax REAL NOT NULL,
                        other REAL NOT NULL
                );
                """;

        try (Connection connection = Database.connect()) {
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
            connection = DriverManager.getConnection(dbPrefix +  location);
        } catch (SQLException exception) {
            return null;
        }
        return connection;
    }
}

/*
public static boolean insertExpense(LocalDate date, float total, float housing, float food, float goingOut, float transportation, float travel, float tax, float other) {
        String sql = "INSERT INTO expense(date, total, housing, food, goingOut, transportation, travel, tax, other) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date.toString());
            pstmt.setFloat(2, total);
            pstmt.setFloat(3, housing);
            pstmt.setFloat(4, food);
            pstmt.setFloat(5, goingOut);
            pstmt.setFloat(6, transportation);
            pstmt.setFloat(7, travel);
            pstmt.setFloat(8, tax);
            pstmt.setFloat(9, other);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
 */
