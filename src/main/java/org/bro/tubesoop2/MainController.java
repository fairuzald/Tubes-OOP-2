package org.bro.tubesoop2;
import javafx.scene.layout.TilePane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.input.*;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import java.io.IOException;
import java.util.Objects;

import javafx.scene.layout.TilePane;
import org.bro.tubesoop2.grid.Grid;
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.state.GameState;
import org.bro.tubesoop2.state.StateLoader;
import org.bro.tubesoop2.state.TextLoader;

public class MainController {
    GameState state;
    private int activeDeckNum = 6;

    @FXML
    private Label player1Name, player2Name, player1Gulden, player2Gulden, activeDeck, turn;

    @FXML
    private Button shopButton, loadButton, myFieldButton, enemyFieldButton, saveButton, pluginButton;


    @FXML
    private DraggableItem[] sourceViews = new DraggableItem[6];

    @FXML
    private DraggableItem[] destinationViews = new DraggableItem[20];

    @FXML
    private TilePane leftDeck, ladangDeck;

    @FXML
    public void initialize() {
        StateLoader loader = new StateLoader();
        state = loader.setPath("state", "gamestate.txt", "player1.txt", "player2.txt")
                .setPlugin(new TextLoader())
//                .setPluginFromJarPath("src/plugin/jar/JsonLoader.jar")
                .loadState();

        for (int i = 0; i < sourceViews.length; i++) {
            if(i%2==0){
                sourceViews[i] = new ProductCard("assets/test.png");

            }else{
                sourceViews[i] = new ItemCard("assets/test.png");
            }
            addDragHandlers(sourceViews[i]);
            leftDeck.getChildren().add(sourceViews[i]);
        }

        for (int i = 0; i < destinationViews.length; i++) {
            destinationViews[i] = new ProductCard("assets/Basic.png");
            addDropHandlers(destinationViews[i]);
            ladangDeck.getChildren().add(destinationViews[i]);
        }

        // Iterasi grid aktif
        Grid<Resource> ladangPlayer = this.state.getCurrentPlayer().getLadang();
        ladangPlayer.forEachActive((a) -> {
            Resource currentElement = ladangPlayer.getElement(a);
            String name = currentElement.getName();
            String imagePath;
            if(Objects.equals(name, "Bear")){
                imagePath = "assets/Hewan/Bear.png";
            } else if (Objects.equals(name, "Chicken")){
                imagePath = "assets/Hewan/Chicken.png";
            } else if (Objects.equals(name, "Cow")){
                imagePath = "assets/Hewan/Cow.png";
            } else if (Objects.equals(name, "Hiu Darat")){
                imagePath = "assets/Hewan/Hiu_Darat.png";
            } else if (Objects.equals(name, "Horse")) {
                imagePath = "assets/Hewan/Horse.png";
            } else if (Objects.equals(name, "Shark")) {
                imagePath = "assets/Hewan/Shark.png";
            } else if (Objects.equals(name, "DOMBA")) {
                imagePath = "assets/Hewan/Sheep.png";
            } else if (Objects.equals(name, "Corn Seeds")) {
                imagePath = "assets/Tanaman/Corn_Seeds.png";
            } else if (Objects.equals(name, "Pumpkin Seeds")) {
                imagePath = "assets/Tanaman/Pumpkin_Seeds.png";
            } else if (Objects.equals(name, "Strawberry Seeds")) {
                imagePath = "assets/Tanaman/Strawberry_Seeds.png";
            } else {
                imagePath = "assets/basic.png";
            }

            // Set Destination Views
            int gridIDX = convertGridToListIdx(a.getCol(),a.getRow());
            destinationViews[gridIDX] = new CreatureCard(imagePath);

            // Update Deck
            ladangDeck.getChildren().remove(gridIDX);
            ladangDeck.getChildren().add(gridIDX,destinationViews[gridIDX]);
        });
    }

    private int convertGridToListIdx(int i, int j){
        return i*4 + j;
    }

    @FXML
    private void addDragHandlers(DraggableItem imageView) {
        imageView.setOnDragDetected(imageView::dragDetected);
        imageView.setOnDragDone(imageView::dragDone);
    }

    @FXML
    private void addDropHandlers(DraggableItem imageView) {
        imageView.setOnDragOver(imageView::dragOver);
        imageView.setOnDragDropped(imageView::dragDropped);
    }

    @FXML
    private void addDetailHandlers(DraggableItem imageView) {
        imageView.setOnMouseClicked(this::onItemClick);
        imageView.setPickOnBounds(true);
    }

    @FXML
    private void dragDetected(MouseEvent event) {
        ImageView sourceView = (ImageView) event.getSource();
        Dragboard dragboard = sourceView.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putImage(sourceView.getImage());
        dragboard.setContent(content);
        event.consume();
    }

    @FXML
    private void dragDone(DragEvent event) {
        if (event.getTransferMode() == TransferMode.MOVE) {
            ImageView sourceView = (ImageView) event.getSource();
            Image myImage = new Image(getClass().getResourceAsStream("assets/basic.png"));
            sourceView.setImage(myImage);
        }
        event.consume();
    }

    @FXML
    private void dragOver(DragEvent event) {
        if (event.getDragboard().hasImage() || event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void onItemClick(MouseEvent event) {
        if (!DetailController.isDetailOpen()) {
            try {
                String itemName = "Corn";
                String[] activeItems = {"Item1", "Item2", "Item3"};
                String age = "5";
                String ageOrWeight = "Age";

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detail.fxml"));
                Parent root = fxmlLoader.load();
                Stage detailStage = new Stage();

                DetailController controller = fxmlLoader.getController();
                controller.updateDetails(itemName, activeItems, age, ageOrWeight);

                detailStage.setTitle("Detail");
                detailStage.setScene(new Scene(root));
                detailStage.show();
                DetailController.setDetailOpen(true);

                detailStage.setOnCloseRequest(eventClose -> DetailController.setDetailOpen(false));
            } catch (IOException e) {
                System.out.println("Error loading detail.fxml: " + e.getMessage());
            }
        }
    }

    @FXML
    void onLoadClick(ActionEvent event) {
        if (!LoadController.isLoadWindowOpen()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("load.fxml"));
                Parent root = fxmlLoader.load();
                Stage loadStage = new Stage();
                loadStage.setTitle("Load State");
                loadStage.setScene(new Scene(root, 602, 318));

                loadStage.setResizable(false);

                loadStage.initModality(Modality.APPLICATION_MODAL);

                loadStage.show();
                LoadController.setLoadWindowOpen(true);
                loadStage.setOnCloseRequest(eventClose -> LoadController.setLoadWindowOpen(false));
            } catch (IOException e) {
                System.out.println("Error loading load.fxml: " + e.getMessage());
            }
        }
    }

    @FXML
    void onSaveClick(ActionEvent event) {
        if (!SaveController.isSaveWindowOpen()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("save.fxml"));
                Parent root = fxmlLoader.load();
                Stage saveStage = new Stage();
                saveStage.setTitle("Save State");
                saveStage.setScene(new Scene(root, 602, 318));

                saveStage.setResizable(false);

                saveStage.initModality(Modality.APPLICATION_MODAL);

                saveStage.show();
                SaveController.setSaveWindowOpen(true);
                saveStage.setOnCloseRequest(eventClose -> SaveController.setSaveWindowOpen(false));
            } catch (IOException e) {
                System.out.println("Error loading save.fxml: " + e.getMessage());
            }
        }
    }

    @FXML
    void onPluginClick(ActionEvent event) {
        if (!PluginController.isPluginWindowOpen()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("plugin.fxml"));
                Parent root = fxmlLoader.load();
                Stage pluginStage = new Stage();
                pluginStage.setTitle("Save State");
                pluginStage.setScene(new Scene(root, 602, 270));

                pluginStage.setResizable(false);

                pluginStage.initModality(Modality.APPLICATION_MODAL);

                pluginStage.show();
                PluginController.setPluginWindowOpen(true);
                pluginStage.setOnCloseRequest(eventClose -> PluginController.setPluginWindowOpen(false));
            } catch (IOException e) {
                System.out.println("Error loading plugin.fxml: " + e.getMessage());
            }
        }
    }

    @FXML
    void onNextClick(ActionEvent event) {
        if (!RandomController.isRandomWindowOpen()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("random.fxml"));
                Parent root = fxmlLoader.load();
                Stage randomStage = new Stage();
                randomStage.setTitle("Random Deck");
                randomStage.setScene(new Scene(root, 274, 475));

                randomStage.setResizable(false);
                randomStage.initModality(Modality.APPLICATION_MODAL);

                randomStage.initStyle(StageStyle.UNDECORATED);
                randomStage.show();
                RandomController.setRandomWindowOpen(true);
                randomStage.setOnCloseRequest(eventClose -> RandomController.setRandomWindowOpen(false));

                applyRedBorderToBearAttacks();
            }catch (IOException e) {
                System.out.println("Error loading random.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void applyRedBorderToBearAttacks() {

    }


    @FXML
    void onShopClick(ActionEvent event) {
        if (!ShopController.isShopWindowOpen()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("shop.fxml"));
                Parent root = fxmlLoader.load();
                Stage shopStage = new Stage();

                shopStage.setTitle("Shop");
                shopStage.setScene(new Scene(root));
                shopStage.show();

                ShopController.setShopWindowOpen(true);

                shopStage.setOnCloseRequest(eventClose -> ShopController.setShopWindowOpen(false));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error loading shop.fxml: " + e.getMessage());
            }
        }
    }

    @FXML
    void enemyFieldButton(ActionEvent event) {

    }
}
