package org.bro.tubesoop2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class RandomController {
    @FXML
    private Button selectButton,refreshButton;

    @FXML
    private ImageView view, view1, view2, view3;


    private List<ImageView> selectedViews = new ArrayList<>();

    @FXML
    void initialize() {
        view.setOnMouseClicked(event -> handleImageViewClick(view));
        view1.setOnMouseClicked(event -> handleImageViewClick(view1));
        view2.setOnMouseClicked(event -> handleImageViewClick(view2));
        view3.setOnMouseClicked(event -> handleImageViewClick(view3));
    }

    private void handleImageViewClick(ImageView imageView) {
        if (!selectedViews.contains(imageView)) {
            selectedViews.add(imageView);
            applyPreferenceEffect(imageView);
        } else {
            selectedViews.remove(imageView);
            resetSelectionEffect(imageView);
        }
    }

    @FXML
    void onRefresh(ActionEvent event) {
        setImage(view, getRandomImage());
        setImage(view1, getRandomImage());
        setImage(view2, getRandomImage());
        setImage(view3, getRandomImage());

        selectedViews.clear();
    }

    @FXML
    void onSelect(ActionEvent event) {
        for (ImageView imageView : selectedViews) {
            applyPreferenceEffect(imageView);
        }

        System.out.println("Selected views: " + selectedViews);

        if (selectedViews.size() >= 2) {
            setRandomWindowOpen(false);
            Stage stage = (Stage) selectButton.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("You must choose at least two images");

            alert.showAndWait();
        }

    }

    private Image getRandomImage() {
        Random random = new Random();
        int randomIndex = random.nextInt(4) + 1;
        return new Image("file:/assets/" + randomIndex + ".png");
    }

    private void setImage(ImageView imageView, Image image) {
        imageView.setImage(image);
    }

    private void applyPreferenceEffect(ImageView imageView) {
        Blend blend = new Blend(BlendMode.COLOR_BURN);
        blend.setOpacity(0.8);

        imageView.setEffect(blend);
    }


    private void resetSelectionEffect(ImageView imageView) {
        imageView.setEffect(null);
    }

    public static boolean isRandomWindowOpen() {
        return randomWindowOpen;
    }

    public static void setRandomWindowOpen(boolean open) {
        randomWindowOpen = open;
    }

    private static boolean randomWindowOpen = false;
}
