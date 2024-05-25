package org.bro.tubesoop2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.io.File;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bro.tubesoop2.action.Action;

public class LoadController {

    @FXML
    private ChoiceBox<String> dropDown;

    @FXML
    private Button backButton, loadButton;

    @FXML
    private Label toastMessage,toastMessageFile;


    public static Action<String> onLoadValid = new Action<String>();

    @FXML
    void initialize() {
        ObservableList<String> formats = FXCollections.observableArrayList("txt");
        dropDown.setItems(formats);
        dropDown.setValue("txt");
    }

    @FXML
    void onBack(ActionEvent event) {
        setLoadWindowOpen(false);
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void onLoad(ActionEvent event) {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose a directory to load");

            String defaultDirectory = System.getProperty("user.dir");
            directoryChooser.setInitialDirectory(new File(defaultDirectory));

            Stage stage = (Stage) loadButton.getScene().getWindow();
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
        Stage stage = (Stage) loadButton.getScene().getWindow();
        stage.close();
        toastMessage.setTextFill(Color.GREEN);
        toastMessage.setText("State Loaded successfully.");
        onLoadValid.Notify(fileName);
    }

    public static boolean isLoadWindowOpen() {
        return loadWindowOpen;
    }

    public static void setLoadWindowOpen(boolean open) {
        loadWindowOpen = open;
    }

    private static boolean loadWindowOpen = false;
}
