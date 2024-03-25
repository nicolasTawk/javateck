module n.midterm_3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens n.midterm_3 to javafx.fxml;
    exports n.midterm_3;
}