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
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.resource.ResourceFactory;
import org.bro.tubesoop2.utils.Utils;

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
    public static List<ImageView> selectedViews = new ArrayList<>();
    private static boolean randomWindowOpen = false;

    public static List<Image> images = new ArrayList<>();
    public static List<Image> selectedImages = new ArrayList<>();
    private static final String DEFAULT_IMAGE_PATH = "assets/EMPTYCARD.png";
    public static Integer maximumCardsCanBeSelected = 0;

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
            resetSelectionEffect(image);
        }
    }

    @FXML
    void onRefresh(ActionEvent event) {
        for (ImageView view : imageViews) {
            setImage(view, getRandomImage());
        }
        clearAllSelectionEffect();
        selectedViews.clear();
    }

    @FXML
    void onSelect(ActionEvent event) {
        if (selectedViews.size() <= maximumCardsCanBeSelected) {
            setRandomWindowOpen(false);
            Stage stage = (Stage) selectButton.getScene().getWindow();
            stage.close();
            onNextDone.Notify(this);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("You must choose at most " + maximumCardsCanBeSelected + " cards");
            alert.showAndWait();
        }
    }

    public void setImages() {
        images = new ArrayList<>();
        try {
            Path path = Paths.get(getClass().getResource("assets/Animal/").toURI());
            Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        Image image = new Image(filePath.toUri().toString());
                        images.add(image);
                    } catch (Exception e) {

                    }
                });


            path = Paths.get(getClass().getResource("assets/Item/").toURI());
            Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        Image image = new Image(filePath.toUri().toString());
                        images.add(image);
                    } catch (Exception e) {

                    }
            });

            path = Paths.get(getClass().getResource("assets/Produk/").toURI());
            Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        Image image = new Image(filePath.toUri().toString());
                        images.add(image);
                    } catch (Exception e) {

                    }
            });

            path = Paths.get(getClass().getResource("assets/Tanaman/").toURI());
            Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        Image image = new Image(filePath.toUri().toString());
                        images.add(image);
                    } catch (Exception e) {

                    }
            });
        } catch (Exception e) {

        }
    }

    private Image getRandomImage() {
        Random random = new Random();
        ResourceFactory rf = new ResourceFactory();

        Image img;
        Resource r;
        do {
            int randomIndex = random.nextInt(images.size());
            img = images.get(randomIndex);
            String relpath = Utils.getRelativePathFromProject(img.getUrl());
            String key = Utils.toResourceFactoryKeys(relpath);

            r = rf.get(key);
        } while (r.getName().equals("BERUANG"));

        return img;
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
