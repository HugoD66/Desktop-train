module com.example.gestionbudget {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;  // Pour la connexion à la base de données
    // requires org.slf4j;

    opens com.example.gestionbudget to javafx.fxml;

    exports com.example.gestionbudget;
    exports com.example.gestionbudget.line;
    exports com.example.gestionbudget.dialog;
    exports com.example.gestionbudget.db;
}