package com.example.gestionbudget.dialog;

import com.example.gestionbudget.db.ExpenseDAO;
import com.example.gestionbudget.line.Line;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.List;

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
    private TableView<Line> tableView;
    @FXML
    public Button btnOk;
    @FXML
    public Label errorReturn;
    @FXML
    public void initialize() {

        isFloatField(total);
        isFloatField(logement);
        isFloatField(nourriture);
        isFloatField(sorties);
        isFloatField(transport);
        isFloatField(voyage);
        isFloatField(impots);
        isFloatField(autres);

        total.setOnKeyReleased(event -> onKeyReleasedProperty());
        logement.setOnKeyReleased(event -> onKeyReleasedProperty());
        nourriture.setOnKeyReleased(event -> onKeyReleasedProperty());
        sorties.setOnKeyReleased(event -> onKeyReleasedProperty());
        transport.setOnKeyReleased(event -> onKeyReleasedProperty());
        voyage.setOnKeyReleased(event -> onKeyReleasedProperty());
        impots.setOnKeyReleased(event -> onKeyReleasedProperty());
        autres.setOnKeyReleased(event -> onKeyReleasedProperty());

        onKeyReleasedProperty();
    }

    public void setTableView(TableView<Line> tableView) {
        this.tableView = tableView;
        btnOk.setDisable(true);
    }

    private void isFloatField(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    Float.parseFloat(newValue);
                    errorReturn.setText("");
                } catch (NumberFormatException e) {
                    errorReturn.setText("Mauvais format pour " + field.getPromptText() + ": " + newValue);
                }
            } else {
                errorReturn.setText("");
            }
        });
    }

    public void onKeyReleasedProperty() {
        boolean disableButtons =
                    total.getText().isEmpty() ||
                    logement.getText().isEmpty() ||
                    nourriture.getText().isEmpty() ||
                    sorties.getText().isEmpty() ||
                    transport.getText().isEmpty() ||
                    voyage.getText().isEmpty() ||
                    impots.getText().isEmpty() ||
                    autres.getText().isEmpty() ||

                    !errorReturn.getText().isEmpty();

        btnOk.setDisable(disableButtons);
    }

    public void onSubmit() {
        try {
            Line line = new Line();

            LocalDate selectedDate = periode.getValue() != null ? periode.getValue() : LocalDate.now();
            line.setPeriode(selectedDate.toString());

            line.setTotal(Float.valueOf(total.getText()));
            line.setLogement(Float.valueOf(logement.getText()));
            line.setNourriture(Float.valueOf(nourriture.getText()));
            line.setSorties(Float.valueOf(sorties.getText()));
            line.setTransport(Float.valueOf(transport.getText()));
            line.setVoyage(Float.valueOf(voyage.getText()));
            line.setImpots(Float.valueOf(impots.getText()));
            line.setAutres(Float.valueOf(autres.getText()));

            boolean inserted = ExpenseDAO.insertExpense(
                    selectedDate,
                    Float.parseFloat(total.getText()),
                    Float.parseFloat(logement.getText()),
                    Float.parseFloat(nourriture.getText()),
                    Float.parseFloat(sorties.getText()),
                    Float.parseFloat(transport.getText()),
                    Float.parseFloat(voyage.getText()),
                    Float.parseFloat(impots.getText()),
                    Float.parseFloat(autres.getText())
            );

            if (inserted) {
                List<Line> expenses = ExpenseDAO.getAllExpenses();
                for (Line expense : expenses) {
                    System.out.println(expense);
                }

                tableView.getItems().add(line);
            } else {
                System.out.println("Échec de l'insertion des données.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Les champs ne sont pas au bon format.");
        } finally {
            periode.getScene().getWindow().hide();
        }
    }

    public void onCancel() {
        periode.setValue(null);
        total.setText(null);
        logement.setText(null);
        nourriture.setText(null);
        sorties.setText(null);
        transport.setText(null);
        voyage.setText(null);
        impots.setText(null);
        autres.setText(null);

        periode.getScene().getWindow().hide();
    }
}
