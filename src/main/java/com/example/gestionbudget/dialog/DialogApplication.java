package com.example.gestionbudget.dialog;

import com.example.gestionbudget.line.Line;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;


public class DialogApplication  extends Application {

    private TableView<Line> tableView;

    public DialogApplication(TableView<Line> tableView) {
        this.tableView = tableView;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DialogApplication.class.getResource("/com/example/gestionbudget/form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 500);
        primaryStage.setTitle("Ajouter une ligne");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
