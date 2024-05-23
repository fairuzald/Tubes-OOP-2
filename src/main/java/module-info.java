module org.bro.tubesoop2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.bro.tubesoop2 to javafx.fxml;
    exports org.bro.tubesoop2;

    uses org.bro.tubesoop2.state.StatePlugin;
}