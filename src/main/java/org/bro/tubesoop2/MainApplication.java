package org.bro.tubesoop2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bro.tubesoop2.state.StateLoader;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1248, 835);

        stage.setTitle("BRO!");
        stage.setScene(scene);

        stage.setMinWidth(1248);
        stage.setMinHeight(835);

        StateLoader state = new StateLoader();
        state.setPath("state", "gamestate.txt", "player1.txt", "player2.txt")
                .setPluginFromJarPath("src/plugin/jar/JsonLoader.jar")
                .initialize();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}