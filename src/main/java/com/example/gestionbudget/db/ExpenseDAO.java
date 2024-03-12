package com.example.gestionbudget.db;

import com.example.gestionbudget.line.Line;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            pstmt.setString(1, formatter.format(date));
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

    public static boolean deleteExpense(Line line) {
        String sql = "DELETE FROM expense WHERE date = ? AND total = ? AND housing = ? AND food = ? AND goingOut = ? AND transportation = ? AND travel = ? AND tax = ? AND other = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, line.getPeriode());
            pstmt.setFloat(2, line.getTotal());
            pstmt.setFloat(3, line.getLogement());
            pstmt.setFloat(4, line.getNourriture());
            pstmt.setFloat(5, line.getSorties());
            pstmt.setFloat(6, line.getTransport());
            pstmt.setFloat(7, line.getVoyage());
            pstmt.setFloat(8, line.getImpots());
            pstmt.setFloat(9, line.getAutres());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting expense: " + e.getMessage());
            return false;
        }
    }

    public static Map<String, Float> getTotalByCategory() {
        Map<String, Float> totals = new HashMap<>();
        String sql = "SELECT SUM(housing) AS housing, SUM(food) AS food, SUM(goingOut) AS goingOut, SUM(transportation) AS transportation, SUM(travel) AS travel, SUM(tax) AS tax, SUM(other) AS other FROM expense";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                totals.put("logement", rs.getFloat("housing"));
                totals.put("nourriture", rs.getFloat("food"));
                totals.put("sorties", rs.getFloat("goingOut"));
                totals.put("transport", rs.getFloat("transportation"));
                totals.put("voyage", rs.getFloat("travel"));
                totals.put("impots", rs.getFloat("tax"));
                totals.put("autres", rs.getFloat("other"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return totals;
    }
}
