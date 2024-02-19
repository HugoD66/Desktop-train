package com.example.gestionbudget.dialog;

import com.example.gestionbudget.line.Line;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DialogController  {
    public TextField periode;
    public TextField total;
    public TextField logement;
    public TextField nourriture;
    public TextField sorties;
    public TextField transport;
    public TextField voyage;
    public TextField impots;
    public TextField autres;
    private TableView<Line> tableView;

    public void setTableView(TableView<Line> tableView) {
        this.tableView = tableView;
    }

    public void onCancel(ActionEvent actionEvent) {
        periode.setText(null);
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

    public void onSubmit(ActionEvent actionEvent) {
        //System.out.println("periode = " + periode.getText());

        Line line = new Line();
        line.setPeriode(periode.getText());
        line.setTotal(Float.valueOf(total.getText()));
        line.setLogement(Float.valueOf(logement.getText()));
        line.setNourriture(Float.valueOf(nourriture.getText()));
        line.setSorties(Float.valueOf(sorties.getText()));
        line.setTransport(Float.valueOf(transport.getText()));
        line.setVoyage(Float.valueOf(voyage.getText()));
        line.setImpots(Float.valueOf(impots.getText()));
        line.setAutres(Float.valueOf(autres.getText()));

        tableView.getItems().add(line);

    }
}
