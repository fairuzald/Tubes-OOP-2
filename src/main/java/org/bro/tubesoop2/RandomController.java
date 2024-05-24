package org.bro.tubesoop2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.bro.tubesoop2.action.Action;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomController {

    @FXML
    private Button selectButton, refreshButton;

    @FXML
    private TilePane deck_selection;

    private List<ImageView> imageViews = new ArrayList<>();
    private List<ImageView> selectedViews = new ArrayList<>();
    private static boolean randomWindowOpen = false;

    private static List<Image> imagesShown = new ArrayList<>();
    private static final String DEFAULT_IMAGE_PATH = "assets/EMPTYCARD.png";

    public static Action<RandomController> onNextDone = new Action<RandomController>();

    @FXML
    void initialize() {
        Image defaultImage = new Image((getClass().getResourceAsStream(DEFAULT_IMAGE_PATH)));
        for (int i = 0; i < 4; i++) {
            ImageView view = new ImageView(defaultImage);
            view.setOnMouseClicked(event -> handleImageViewClick(view));
            imageViews.add(view);
            deck_selection.getChildren().add(view);
        }

        setImages();
        onRefresh(null);
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

    private void clearAllSelectionEffect() {
        for (ImageView image : selectedViews) {
            selectedViews.remove(image);
            resetSelectionEffect(image);
        }
    }

    @FXML
    void onRefresh(ActionEvent event) {
        for (ImageView view : imageViews) {
            setImage(view, getRandomImage());
        }
        selectedViews.clear();

        clearAllSelectionEffect();
    }

    @FXML
    void onSelect(ActionEvent event) {
        if (selectedViews.size() >= 2) {
            setRandomWindowOpen(false);
            Stage stage = (Stage) selectButton.getScene().getWindow();
            stage.close();
            onNextDone.Notify(this);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("You must choose at least two images");
            alert.showAndWait();
        }
    }

    public void setImages() {
        imagesShown = new ArrayList<>();
        try {
            Path path = Paths.get(getClass().getResource("assets/Hewan/").toURI());
            Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        Image image = new Image(filePath.toUri().toString());
                        imagesShown.add(image);
                    } catch (Exception e) {

                    }
                });


            path = Paths.get(getClass().getResource("assets/Item/").toURI());
            Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        Image image = new Image(filePath.toUri().toString());
                        imagesShown.add(image);
                    } catch (Exception e) {

                    }
            });

            path = Paths.get(getClass().getResource("assets/Produk/").toURI());
            Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        Image image = new Image(filePath.toUri().toString());
                        imagesShown.add(image);
                    } catch (Exception e) {

                    }
            });

            path = Paths.get(getClass().getResource("assets/Tanaman/").toURI());
            Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        Image image = new Image(filePath.toUri().toString());
                        imagesShown.add(image);
                    } catch (Exception e) {

                    }
            });
        } catch (Exception e) {

        }
    }

    private Image getRandomImage() {
        Random random = new Random();
        int randomIndex = random.nextInt(imagesShown.size());

        Image testImage = imagesShown.get(randomIndex);
        System.out.println(testImage.getUrl());
        return testImage;
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
}
