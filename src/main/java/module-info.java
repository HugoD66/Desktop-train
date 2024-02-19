module com.example.gestionbudget {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gestionbudget to javafx.fxml;

    exports com.example.gestionbudget;
    exports com.example.gestionbudget.line;
    exports com.example.gestionbudget.dialog;
}