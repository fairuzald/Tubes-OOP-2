module org.bro.tubesoop2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.naming;
    requires com.fasterxml.jackson.databind;


    opens org.bro.tubesoop2 to javafx.fxml;
    exports org.bro.tubesoop2;

    exports org.bro.tubesoop2.state;
    uses org.bro.tubesoop2.state.StatePlugin;
}