package org.bro.tubesoop2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.bro.tubesoop2.state.GameState;
import org.bro.tubesoop2.state.StateLoader;
import org.bro.tubesoop2.state.TextLoader;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class MainApplication extends Application {
    private static boolean firstTime = true;

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

        // Disable the back button the first time the load stage is shown
        if (firstTime) {
            Button backButton = (Button) loadScene.lookup("#backButton");
            if (backButton != null) {
                backButton.setDisable(true);
            }
            firstTime = false;
        }
        try {
            String musicFile = getClass().getResource("assets/music.mp3").toString();
            Media sound = new Media(musicFile); // Create Media object directly from URL string

            // Create a MediaPlayer
            MediaPlayer mediaPlayer = new MediaPlayer(sound);

            // Set the MediaPlayer to loop the music
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            // Start playing the music
            mediaPlayer.play();
        }catch (Exception e){
            System.out.println(e.getMessage());
        };


        // Show both stages
        mainStage.show();
        loadStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}