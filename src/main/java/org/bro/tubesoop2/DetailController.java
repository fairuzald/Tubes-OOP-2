package org.bro.tubesoop2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DetailController {

    @FXML
    private Label ageLabel,editableLabel,activeItemLabel,itemNameLabel;

    @FXML
    private Button backButton;

    @FXML
    private ImageView detailView;

    @FXML
    void onBack(ActionEvent event) {
        setDetailOpen(false);
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void onHarvest(ActionEvent event) {
    }

    public static boolean isDetailOpen() {
        return detailOpen;
    }

    public static void setDetailOpen(boolean open) {
        detailOpen = open;
    }

    private static boolean detailOpen = false;

    public void updateDetails(String itemName, String[] activeItems, String age, String ageOrWeight) {
        itemNameLabel.setText(itemName);
        editableLabel.setText(ageOrWeight);

        ObservableList<String> activeItemsList = FXCollections.observableArrayList(activeItems);
        String itemsText = String.join(", ", activeItemsList);
        activeItemLabel.setText(itemsText);

        ageLabel.setText(age);
    }
}
