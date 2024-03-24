package com.example.gestionbudget;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;
import com.example.gestionbudget.header.HeaderController;

//GIT REFERENCE : https://github.com/Jicay/FinanceTrackerDemo

public class TableApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionbudget/table-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1030, 550);
        stage.setTitle("EconoMe");
        stage.getIcons().add(new Image("/com/example/gestionbudget/assets/picture/budget-icon.png"));
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        boolean dbInitialized = com.example.gestionbudget.db.Database.isOK();
        if (dbInitialized) {
            System.out.println("La base de donnees a ete correctement initialisee.");
        } else {
            System.out.println("Echec de l'initialisation de la base de donnees.");
            return;
        }

        launch(args);
    }
}

