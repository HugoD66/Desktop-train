package com.example.gestionbudget.header;

import com.example.gestionbudget.TableApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;

// HeaderController.java
public class HeaderController {

    @FXML
    private Parent root;


    @FXML
    private void handleTable() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TableApplication.class.getResource("table-view.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1030, 550);
        stage.setScene(scene);
        stage.show();    }

    @FXML
    private void handleGraphics() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TableApplication.class.getResource("graph-view.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1030, 550);
        stage.setScene(scene);
        stage.show();
    }

}
