package com.example.gestionbudget.dialog;

import com.example.gestionbudget.line.Line;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;


public class DialogApplication   {

    private TableView<Line> tableView;

    public DialogApplication(TableView<Line> tableView) {
        this.tableView = tableView;
    }

}
