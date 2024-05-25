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
    public void start(Stage mainStage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene mainScene = new Scene(mainLoader.load(), 1248, 835);

        mainStage.setTitle("BRO!");
        mainStage.setScene(mainScene);

        mainStage.setMinWidth(1248);
        mainStage.setMinHeight(835);

        // Load the load.fxml
        FXMLLoader loadLoader = new FXMLLoader(MainApplication.class.getResource("load.fxml"));
        Scene loadScene = new Scene(loadLoader.load());
        Stage loadStage = new Stage();

        // Set the owner of the load stage to the main stage
        loadStage.initOwner(mainStage);
        loadStage.setScene(loadScene);

        // Set the load stage to always be on top and not resizable
        loadStage.setAlwaysOnTop(true);
        loadStage.setResizable(false);

        // Add an event handler to the load stage's close request
        loadStage.setOnCloseRequest(event -> {
            mainStage.close();
        });

        // Show both stages
        mainStage.show();
        loadStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}