package org.bro.tubesoop2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bro.tubesoop2.state.GameState;
import org.bro.tubesoop2.state.StateLoader;
import org.bro.tubesoop2.state.TextLoader;

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

        StateLoader loader = new StateLoader();
        GameState state = loader.setPath("state", "gamestate.txt", "player1.txt", "player2.txt")
                .setPlugin(new TextLoader())
//                .setPluginFromJarPath("src/plugin/jar/JsonLoader.jar")
                .loadState();

        System.out.println("===========================");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}