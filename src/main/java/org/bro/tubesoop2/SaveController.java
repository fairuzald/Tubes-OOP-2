package org.bro.tubesoop2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import org.bro.tubesoop2.action.Action;

import java.io.File;

public class SaveController {

    @FXML
    private ChoiceBox<String> dropDown;

    @FXML
    private Button backButton,saveButton;

    @FXML
    private Label toastMessage,toastMessageFile;

    public static Action<String> onSaveValid = new Action<String>();

    @FXML
    void initialize() {
//        ObservableList<String> formats = FXCollections.observableArrayList("txt", "xml", "yaml", "json");
        ObservableList<String> formats = FXCollections.observableArrayList("txt");
        dropDown.setItems(formats);
        dropDown.setValue("txt");
    }

    @FXML
    void onBack(ActionEvent event) {
        setSaveWindowOpen(false);
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void onSave(ActionEvent event) {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose a directory to save");

            String defaultDirectory = System.getProperty("user.dir");
            directoryChooser.setInitialDirectory(new File(defaultDirectory));

            Stage stage = (Stage) saveButton.getScene().getWindow();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                toastMessageFile.setTextFill(Color.GREEN);
                toastMessageFile.setText(selectedDirectory.getAbsolutePath());
            } else {
                toastMessageFile.setTextFill(Color.RED);
                toastMessageFile.setText("No directory selected.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            toastMessageFile.setTextFill(Color.RED);
            toastMessageFile.setText("Error selecting directory.");
        }
    }

    @FXML
    void onSubmit(ActionEvent event) {
        String fileName = toastMessageFile.getText();

        if(fileName.equals("")){
            toastMessage.setTextFill(Color.RED);
            toastMessage.setText("Please select folder.");
            return;
        }

        toastMessage.setTextFill(Color.GREEN);
        toastMessage.setText("File saved successfully.");
        onSaveValid.Notify(fileName);
    }

    public static boolean isSaveWindowOpen() {
        return saveWindowOpen;
    }

    public static void setSaveWindowOpen(boolean open) {
        saveWindowOpen = open;
    }

    private static boolean saveWindowOpen = false;
}
