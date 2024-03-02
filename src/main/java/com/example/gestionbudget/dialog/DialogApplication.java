package com.example.gestionbudget.dialog;

import com.example.gestionbudget.line.Line;
import javafx.scene.control.TableView;

public class DialogApplication   {

    private TableView<Line> tableView;

    public DialogApplication(TableView<Line> tableView) {
        this.tableView = tableView;
    }

}
