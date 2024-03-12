module com.example.gestionbudget {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;

    opens com.example.gestionbudget to javafx.fxml;
    opens com.example.gestionbudget.dialog to javafx.fxml;

    exports com.example.gestionbudget;
    exports com.example.gestionbudget.line;
    exports com.example.gestionbudget.dialog;
    exports com.example.gestionbudget.db;
}