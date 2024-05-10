module org.bro.tubesoop2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.bro.tubesoop2 to javafx.fxml;
    exports org.bro.tubesoop2;
}