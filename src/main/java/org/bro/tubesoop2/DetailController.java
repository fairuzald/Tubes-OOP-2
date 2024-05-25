package org.bro.tubesoop2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.bro.tubesoop2.action.Action;
import org.bro.tubesoop2.animal.Animal;
import org.bro.tubesoop2.creature.Creature;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DetailController {

    @FXML
    private Label ageLabel,editableLabel,activeItemLabel,itemNameLabel;

    @FXML
    private Button backButton;

    @FXML
    private ImageView detailView;

    Creature currentCreature;
    int locationIndex;

    public static Action<Integer> onHarvestClicked = new Action<Integer>();

    @FXML
    void onBack(ActionEvent event) {
        setDetailOpen(false);
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void onHarvest(ActionEvent event) {
        onHarvestClicked.Notify(locationIndex);
        setDetailOpen(false);
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }

    public static boolean isDetailOpen() {
        return detailOpen;
    }

    public static void setDetailOpen(boolean open) {
        detailOpen = open;
    }

    private static boolean detailOpen = false;

    public void updateDetails(Creature creature, int locationIndex) {
        this.currentCreature = creature;
        this.locationIndex = locationIndex;

        String itemName = creature.getFormattedName();
        String[] activeItems = creature.getItemsActive().stream().map(item -> item.getName()).toArray(String[]::new);
        String value = Integer.toString(creature.getUmurOrBerat());
        String label = creature instanceof Animal ? "Berat" : "Umur";


        itemNameLabel.setText(itemName);
        editableLabel.setText(label);

        ObservableList<String> activeItemsList = FXCollections.observableArrayList(activeItems);
        Map<String, Integer> groupedActiveItems = new HashMap<String, Integer>();

        for (String activeItem : activeItemsList) {
            if (groupedActiveItems.containsKey(activeItem)) {
                groupedActiveItems.put(activeItem, groupedActiveItems.get(activeItem) + 1);
            } else {
                groupedActiveItems.put(activeItem, 1);
            }
        }

        String itemsText = "";
        for (Map.Entry<String, Integer> entry : groupedActiveItems.entrySet()) {
            itemsText += entry.getKey() + "(" + entry.getValue() + "), ";
        }


        if(itemsText.isEmpty()) itemsText = "Tidak ada";
        activeItemLabel.setText(itemsText);

        Image img = new Image(getClass().getResourceAsStream(Card.createCard(creature).getImagePath()));
        detailView.setImage(img);

        ageLabel.setText(value);
    }
}
