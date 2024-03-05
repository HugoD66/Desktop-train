package com.example.gestionbudget.db;

import com.example.gestionbudget.line.Line;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    /*
    Un objet d'accès aux données (en anglais data access object ou DAO)
    est un patron de conception (c'est-à-dire un modèle pour concevoir
    une solution) utilisé dans les architectures logicielles objet.
    */

    public static List<Line> getAllExpenses() {
        List<Line> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expense";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Line expense = new Line();
                expense.setPeriode(rs.getString("date"));
                expense.setTotal(rs.getFloat("total"));
                expense.setLogement(rs.getFloat("housing"));
                expense.setNourriture(rs.getFloat("food"));
                expense.setSorties(rs.getFloat("goingOut"));
                expense.setTransport(rs.getFloat("transportation"));
                expense.setVoyage(rs.getFloat("travel"));
                expense.setImpots(rs.getFloat("tax"));
                expense.setAutres(rs.getFloat("other"));
                expenses.add(expense);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expenses;
    }

    public static boolean insertExpense(LocalDate date, float total, float housing, float food, float goingOut, float transportation, float travel, float tax, float other) {
        String sql = "INSERT INTO expense(date, total, housing, food, goingOut, transportation, travel, tax, other) VALUES(?,?,?,?,?,?,?,?,?)";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, formatter.format(date)); // Utilisez formatter.format(date) au lieu de date.toString()
            pstmt.setFloat(2, total);
            pstmt.setFloat(3, housing);
            pstmt.setFloat(4, food);
            pstmt.setFloat(5, goingOut);
            pstmt.setFloat(6, transportation);
            pstmt.setFloat(7, travel);
            pstmt.setFloat(8, tax);
            pstmt.setFloat(9, other);
            pstmt.executeUpdate();
            System.out.println("Expense added");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

