package org.bro.tubesoop2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.bro.tubesoop2.animal.Animal;
import org.bro.tubesoop2.creature.Creature;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DetailController {

    @FXML
    private Label ageLabel,editableLabel,activeItemLabel,itemNameLabel;

    @FXML
    private Button backButton;

    @FXML
    private ImageView detailView;

    Creature currentCreature;

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

    public void updateDetails(Creature creature) {
        this.currentCreature = creature;

        String itemName = creature.getFormattedName();
        String[] activeItems = creature.getItemsActive().stream().map(item -> item.getName()).toArray(String[]::new);
        String value = Integer.toString(creature.getUmurOrBerat());
        String label = creature instanceof Animal ? "Berat" : "Umur";


        itemNameLabel.setText(itemName);
        editableLabel.setText(label);

        ObservableList<String> activeItemsList = FXCollections.observableArrayList(activeItems);
        String itemsText = String.join(", ", activeItemsList);
        if(itemsText.isEmpty()) itemsText = "Tidak ada";
        activeItemLabel.setText(itemsText);

        Image img = new Image(getClass().getResourceAsStream(Card.createCard(creature).getImagePath()));
        detailView.setImage(img);

        ageLabel.setText(value);
    }
}
