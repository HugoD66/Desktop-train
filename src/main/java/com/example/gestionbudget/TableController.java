package com.example.gestionbudget;

import com.example.gestionbudget.dialog.DialogApplication;
import com.example.gestionbudget.line.Line;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;


public class TableController {

    @FXML
    private TableView<Line> tableView;

    @FXML
    protected void onHelloButtonClick() {
        try {
            DialogApplication dialogApp = new DialogApplication(tableView);

            //DialogApplication.class.newInstance().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}