package com.example.gestionbudget;
import com.example.gestionbudget.db.ExpenseDAO;
import com.example.gestionbudget.line.Line;
import javafx.event.ActionEvent;
import javafx.scene.chart.*;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class GraphicController {

    @FXML
    private PieChart pieChart;

    @FXML
    private LineChart<String, Float> lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ChoiceBox<String> periodChoiceBox;

    public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM yy");
    private final static DateTimeFormatter FULL_DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy");

    public void initialize() {
        LocalDate date = LocalDate.now();

        loadExpenses(date);

        for (int i = 0; i < 12; i++) {
            periodChoiceBox.getItems().add(date.format(FULL_DATE_FORMAT));
            date = date.minusMonths(1);
        }
        periodChoiceBox.getSelectionModel().selectFirst();
    }

    private List<Line> loadExpenses(LocalDate currentMonth) {

        List<Line> lastExpenses = ExpenseDAO.findLastExpensesEndingAtCurrentMonth(12, currentMonth);

        if (lastExpenses.isEmpty()) {
            return null;
        }

        pieChart.getData().clear();
        lineChart.getData().clear();

        if (!lastExpenses.isEmpty()) {
            Line firstExpense = lastExpenses.get(0);

            pieChart.getData().addAll(
                    new PieChart.Data("logement", firstExpense.getLogement()),
                    new PieChart.Data("nourriture", firstExpense.getNourriture()),
                    new PieChart.Data("sortie", firstExpense.getSorties()),
                    new PieChart.Data("transport", firstExpense.getTransport()),
                    new PieChart.Data("voyage", firstExpense.getVoyage()),
                    new PieChart.Data("impôts", firstExpense.getImpots()),
                    new PieChart.Data("autres", firstExpense.getAutres())
            );
        }

        XYChart.Series<String, Float> seriesHousing = new XYChart.Series<>();
        seriesHousing.setName("logement");
        XYChart.Series<String, Float> seriesFood = new XYChart.Series<>();
        seriesFood.setName("nourriture");
        XYChart.Series<String, Float> seriesGoingOut = new XYChart.Series<>();
        seriesGoingOut.setName("sortie");
        XYChart.Series<String, Float> seriesTransportation = new XYChart.Series<>();
        seriesTransportation.setName("transport");
        XYChart.Series<String, Float> seriesTravel = new XYChart.Series<>();
        seriesTravel.setName("voyage");
        XYChart.Series<String, Float> seriesTax = new XYChart.Series<>();
        seriesTax.setName("impôts");
        XYChart.Series<String, Float> seriesOther = new XYChart.Series<>();
        seriesOther.setName("autres");

        lastExpenses.stream().sorted(Comparator.comparing(Line::getPeriode)).forEach(line -> {
            seriesHousing.getData().add(new XYChart.Data<>(line.getPeriode(), line.getLogement()));
            seriesFood.getData().add(new XYChart.Data<>(line.getPeriode(), line.getNourriture()));
            seriesGoingOut.getData().add(new XYChart.Data<>(line.getPeriode(), line.getSorties()));
            seriesTransportation.getData().add(new XYChart.Data<>(line.getPeriode(), line.getTransport()));
            seriesTravel.getData().add(new XYChart.Data<>(line.getPeriode(), line.getVoyage()));
            seriesTax.getData().add(new XYChart.Data<>(line.getPeriode(), line.getImpots()));
            seriesOther.getData().add(new XYChart.Data<>(line.getPeriode(), line.getAutres()));
        });


        lineChart.getData().addAll(
                seriesHousing,
                seriesFood,
                seriesGoingOut,
                seriesTransportation,
                seriesTravel,
                seriesTax,
                seriesOther
        );
        return lastExpenses;
    }

    public void changePeriod(ActionEvent actionEvent) {
        var periodSelected = periodChoiceBox.getSelectionModel().getSelectedItem();
        LocalDate dateSelected = LocalDate.parse("01 " + periodSelected, DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        loadExpenses(dateSelected);
    }

}

