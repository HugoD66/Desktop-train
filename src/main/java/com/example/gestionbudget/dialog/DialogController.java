package com.example.gestionbudget.dialog;

import com.example.gestionbudget.db.ExpenseDAO;
import com.example.gestionbudget.line.Line;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

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

    @FXML
    private ImageView totalIcon;

    @FXML
    private Label totalTotal, totalLogement, totalNourriture, totalSorties, totalTransport, totalVoyage, totalImpots, totalAutres;


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


        //Ajout icone Total content
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/gestionbudget/assets/picture/total-icon.png")));
        totalIcon.setImage(icon);

        //Importation des données de la DB
        List<Line> linesFromDB = getAllExpenses();
        items.addAll(linesFromDB);
        tableView.setItems(items);


        //Event pour la suppression ligne table
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                onTableClick();
            }
        });

        updateTotals();

    }

    public void addLine(ActionEvent event) {
        Dialog<Line> addPersonDialog = new LineDialog();
        Optional<Line> result = addPersonDialog.showAndWait();
        result.ifPresent(items::add);

        updateTotals();

        event.consume();
    }

    public void onTableClick () {
        Line selectedLine = tableView.getSelectionModel().getSelectedItem();
        if (selectedLine != null) {
            lineSelected = selectedLine;
        }
    }

    public void deleteLine() {
        Line selectedLine = tableView.getSelectionModel().getSelectedItem();
        if (selectedLine != null) {
            items.remove(selectedLine);
            deleteExpense(selectedLine);

            updateTotals();

        }
    }


    public void updateTotals() {
        Map<String, Float> totals = ExpenseDAO.getTotalByCategory();
        totalLogement.setText("Logement : " + totals.getOrDefault("logement", 0f).toString() + " €");
        totalNourriture.setText("Nourriture : " + totals.getOrDefault("nourriture", 0f).toString() + " €");
        totalSorties.setText("Sorties : " + totals.getOrDefault("sorties", 0f).toString() + " €");
        totalTransport.setText("Transport : " + totals.getOrDefault("transport", 0f).toString() + " €");
        totalVoyage.setText("Voyage : " + totals.getOrDefault("voyage", 0f).toString() + " €");
        totalImpots.setText("Impôts : " + totals.getOrDefault("impots", 0f).toString() + " €");
        totalAutres.setText("Autres : " + totals.getOrDefault("autres", 0f).toString() + " €");



        float totalGeneral = totals.values().stream().reduce(0f, Float::sum);

        totalTotal.setText("Total : " + totalGeneral + " €");
    }

}
