package com.example.gestionbudget.dialog;

import com.example.gestionbudget.line.Line;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.gestionbudget.db.ExpenseDAO.deleteExpense;
import static com.example.gestionbudget.db.ExpenseDAO.getAllExpenses;

public class DialogController {
    public DatePicker periode;
    public TextField total;
    public TextField logement;
    public TextField nourriture;
    public TextField sorties;
    public TextField transport;
    public TextField voyage;
    public TextField impots;
    public TextField autres;

    public Line lineSelected;

    @FXML
    private TableView<Line> tableView;

    private final ObservableList<Line> items = FXCollections.observableArrayList();

    public void initialize() {

        /*
        items.addAll(
                new Line("2021-02-02", 250.0f, 160.45f, 110.0f, 210.0f, 95.0f, 35.0f, 5.0f),
                new Line("2022-03-03", 300.0f, 170.50f, 120.0f, 220.0f, 100.0f, 40.0f, 10.0f),
                new Line("2023-04-04", 350.0f, 180.55f, 130.0f, 230.0f, 105.0f, 45.0f, 15.0f),
                new Line("2024-05-05", 400.0f, 190.60f, 140.0f, 240.0f, 110.0f, 50.0f, 20.0f),
                new Line("2025-06-06", 450.0f, 200.65f, 150.0f, 250.0f, 115.0f, 55.0f, 25.0f)
        );
         */
        List<Line> linesFromDB = getAllExpenses();
        items.addAll(linesFromDB);

        tableView.setItems(items);

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                onTableClick();
            }
        });

    }

    public void addLine(ActionEvent event) {
        Dialog<Line> addPersonDialog = new LineDialog();
        Optional<Line> result = addPersonDialog.showAndWait();
        result.ifPresent(items::add);

        event.consume();
    }

    public void onTableClick () {
        Line selectedLine = tableView.getSelectionModel().getSelectedItem();
        if (selectedLine != null) {
            lineSelected = selectedLine;
            System.out.println(selectedLine);
        }
    }

    public void deleteLine() {
        Line selectedLine = tableView.getSelectionModel().getSelectedItem();
        if (selectedLine != null) {
            items.remove(selectedLine);
            deleteExpense(selectedLine);
        }
    }
}
