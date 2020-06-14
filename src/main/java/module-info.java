module centus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires activejdbc;
    requires jbcrypt;

    opens centus to javafx.fxml;
    exports centus;
    exports centus.models;
}