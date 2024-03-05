package com.example.gestionbudget.dialog;

import com.example.gestionbudget.line.Line;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.UnaryOperator;

import static com.example.gestionbudget.db.ExpenseDAO.insertExpense;


public class LineDialog extends Dialog<Line> {
    @FXML
    private DatePicker periode;
    @FXML
    private TextField logement;
    @FXML
    private TextField nourriture;
    @FXML
    private TextField sorties;
    @FXML
    private TextField transport;
    @FXML
    private TextField voyage;
    @FXML
    private TextField impots;
    @FXML
    private TextField autres;

    @FXML
    private ButtonType createButton;

    public LineDialog() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/gestionbudget/form-view.fxml"));
            fxmlLoader.setController(this);

            //Scene scene = new Scene(fxmlLoader.load(), 300, 550);
            DialogPane dialogPane = fxmlLoader.load();


            setTitle("Ajouter une dépense");
            setDialogPane(dialogPane);
            initResultConverter();
            // Disable button when all field are not filled
            computeIfButtonIsDisabled();

            // Ensure only numeric input are set in the fields
            forceDoubleFormat();

            ButtonBar buttonBar = (ButtonBar)dialogPane.lookup(".button-bar");
            buttonBar.getButtons().forEach(button -> {
                if (button instanceof ButtonBase) {
                    ((ButtonBase) button).getStyleClass().add("dialog-button");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void forceDoubleFormat() {
        UnaryOperator<TextFormatter.Change> numberValidationFormatter = t -> {
            if (t.isReplaced())
                if(t.getText().matches("[^0-9]"))
                    t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));


            if (t.isAdded()) {
                if (t.getControlText().contains(".")) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText("");
                    }
                } else if (t.getText().matches("[^0-9.]")) {
                    t.setText("");
                }
            }
            return t;
        };
        logement.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        nourriture.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        sorties.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        transport.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        voyage.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        impots.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        autres.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
    }
    private void computeIfButtonIsDisabled() {
        getDialogPane().lookupButton(createButton).disableProperty().bind(
                Bindings.createBooleanBinding(() ->periode.getValue() == null, periode.valueProperty())
                        .or(Bindings.createBooleanBinding(() -> logement.getText().trim().isEmpty(), logement.textProperty())
                                .or(Bindings.createBooleanBinding(() -> nourriture.getText().trim().isEmpty(), nourriture.textProperty())
                                        .or(Bindings.createBooleanBinding(() -> sorties.getText().trim().isEmpty(), sorties.textProperty())
                                                .or(Bindings.createBooleanBinding(() -> transport.getText().trim().isEmpty(), transport.textProperty())
                                                        .or(Bindings.createBooleanBinding(() -> voyage.getText().trim().isEmpty(), voyage.textProperty())
                                                                .or(Bindings.createBooleanBinding(() -> impots.getText().trim().isEmpty(), impots.textProperty())
                                                                        .or(Bindings.createBooleanBinding(() -> autres.getText().trim().isEmpty(), autres.textProperty())
                                                                        ))))
                                                ))));
    }

    private void initResultConverter() {
        setResultConverter(buttonType -> {
            if (!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                return null;
            }
            LocalDate selectedDate = periode.getValue();
            String periodeString = selectedDate != null ? selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";

            Line line = new Line(
                    periodeString,
                    Float.parseFloat(logement.getText()),
                    Float.parseFloat(nourriture.getText()),
                    Float.parseFloat(sorties.getText()),
                    Float.parseFloat(transport.getText()),
                    Float.parseFloat(voyage.getText()),
                    Float.parseFloat(impots.getText()),
                    Float.parseFloat(autres.getText())
            );
            insertExpense(LocalDate.parse(periodeString, DateTimeFormatter.ofPattern("yyyy-MM-dd")), line.getTotal(), line.getLogement(), line.getNourriture(), line.getSorties(), line.getTransport(), line.getVoyage(), line.getImpots(), line.getAutres());
            return line;
        });
    }

    @FXML
    private void initialize() {

    }

}
