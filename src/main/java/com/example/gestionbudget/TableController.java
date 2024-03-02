package com.example.gestionbudget;

import com.example.gestionbudget.dialog.DialogController;
import com.example.gestionbudget.line.Line;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class TableController {

    @FXML
    private TableView<Line> tableView;

    @FXML
    protected void onHelloButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/gestionbudget/form-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 300, 550);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ajouter une dépense");
            dialogStage.getIcons().add(new Image("./com/example/gestionbudget/assets/picture/budget-icon.png"));
            dialogStage.setScene(scene);
            DialogController controller = fxmlLoader.getController();
            controller.setTableView(tableView);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}