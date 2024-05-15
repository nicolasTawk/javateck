module n.midterm_3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.net.http;
    requires org.json;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens n.midterm_3 to javafx.fxml;
    exports n.midterm_3;
}