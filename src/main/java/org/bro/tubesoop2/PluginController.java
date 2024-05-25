package org.bro.tubesoop2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class PluginController {

    @FXML
    private Button backButton,pluginButton;

    @FXML
    private Label toastMessage,toastMessageFile;

    @FXML
    void onBack(ActionEvent event) {
        setPluginWindowOpen(false);
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }


    @FXML
    void onLoadPlugin(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose a plugin file");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jar Files", "*.jar"));

            String defaultDirectory = System.getProperty("user.dir");
            fileChooser.setInitialDirectory(new File(defaultDirectory));

            Stage stage = (Stage) pluginButton.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                toastMessageFile.setTextFill(Color.GREEN);
                toastMessageFile.setText("Plugin loaded: " + selectedFile.getName());
            } else {
                toastMessageFile.setTextFill(Color.RED);
                toastMessageFile.setText("No plugin selected.");
            }
        } catch (Exception e) {
            toastMessageFile.setTextFill(Color.RED);
            toastMessageFile.setText("Error loading plugin.");
        }
    }

    @FXML
    void onSubmit(ActionEvent event) {
        File selectedFile = new File(toastMessageFile.getText());
        String fileExtension = getExtension(selectedFile);

        if ("jar".equalsIgnoreCase(fileExtension)) {
            toastMessage.setTextFill(Color.GREEN);
            toastMessage.setText("File is a valid jar.");
        } else {
            toastMessage.setTextFill(Color.RED);
            toastMessage.setText("File is not a jar.");
        }
        Stage stage = (Stage) pluginButton.getScene().getWindow();
        stage.close();
    }

    private String getExtension(File file) {
        String fileName = file.getName();
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex > 0) {
            return fileName.substring(lastIndex + 1).toLowerCase();
        }
        return "";
    }

    public static boolean isPluginWindowOpen() {
        return pluginWindowOpen;
    }

    public static void setPluginWindowOpen(boolean open) {
        pluginWindowOpen = open;
    }

    private static boolean pluginWindowOpen = false;
}
