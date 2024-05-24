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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.input.*;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javafx.scene.layout.TilePane;
import org.bro.tubesoop2.grid.Grid;
import org.bro.tubesoop2.grid.Location;
import org.bro.tubesoop2.player.Player;
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.state.GameState;
import org.bro.tubesoop2.state.StateLoader;
import org.bro.tubesoop2.state.TextLoader;

public class MainController {
    GameState state;
    private int activeDeckNum = 6;
    RandomController randomController = new RandomController();

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
                .loadState();

        for (int i = 0; i < sourceViews.length; i++) {
            if(i%3==0){
                sourceViews[i] = new ProductCard("assets/Produk/corn.png");

            }
            else if(i%3==1) {
                sourceViews[i] = new ItemCard("assets/Item/Accelerate.png");

            } else{
                sourceViews[i] = new CreatureCard("assets/Hewan/Bear.png");

            }
            leftDeck.getChildren().add(sourceViews[i]);
        }

        for (int i = 0; i < destinationViews.length; i++) {
            destinationViews[i] = new EmptyCard();
            ladangDeck.getChildren().add(destinationViews[i]);
        }

        /**
         * Set Active Deck
         * */
        List<Resource> activeDeckPlayer = this.state.getCurrentPlayer().getActiveDeck();
        for(int i = 0; i < activeDeckPlayer.size(); i++) {
            Resource resource = activeDeckPlayer.get(i);
            if(resource != null) {
                String name = resource.getName();
                sourceViews[i] = CreatureCard.getCreatureCard(name);
                leftDeck.getChildren().set(i,sourceViews[i]);
            }
        }

        /**
         * Set Ladang
         * */
        // Iterasi grid aktif
        Grid<Resource> ladangPlayer = this.state.getCurrentPlayer().getLadang();
        ladangPlayer.forEachActive((a) -> {
            Resource currentElement = ladangPlayer.getElement(a);
            String name = currentElement.getName();

            // Set Destination Views
            int gridIDX = convertGridToListIdx(a.getCol(),a.getRow());
            destinationViews[gridIDX] = CreatureCard.getCreatureCard(name);

            // Update Deck
            ladangDeck.getChildren().remove(gridIDX);
            ladangDeck.getChildren().add(gridIDX,destinationViews[gridIDX]);
        });

        EmptyCard.onDrop.AddListener(tup -> {
            Resource rsc = tup.getFirst();
            Integer index = tup.getSecond();
            int[] gridPosition = convertListIdxToGrid(index);
            int row = gridPosition[0];
            int col = gridPosition[1];
            Location lct = new Location(row, col);
            state.getCurrentPlayer().addLadang(rsc, lct);
        });


//        state = loader.setPath("state", "gamestate.txt", "player1.txt", "player2.txt")
//                .setPluginFromJarPath("src/plugin/jar/JsonLoader.jar")
//                .setPlugin(new TextLoader())
//                .loadState();
        state = new GameState();

        updateGUI(state);
        RandomController.onNextDone.AddListener(r -> {
            state.NextTurn();
            updateGUI(state);
        });

        SaveController.onSaveValid.AddListener(folderDir -> {
            loader.setPath(folderDir, "gamestate_.txt", "player1_.txt", "player2_.txt")
                    .setPlugin(new TextLoader())
                    .saveState(state);
            updateGUI(state);
        });

        LoadController.onLoadValid.AddListener(folderDir -> {
            state = loader.setPath(folderDir, "gamestate.txt", "player1.txt", "player2.txt")
                    .setPlugin(new TextLoader())
                    .loadState();
            updateGUI(state);
        });
    }

    void updateGUI(GameState state){
        Integer turn = state.getTurn();
        this.turn.setText(turn.toString());
        if(turn % 2 == 1){
            player1Name.setTextFill(Color.WHITE);
            player2Name.setTextFill(Color.GRAY);
            player1Gulden.setTextFill(Color.WHITE);
            player2Gulden.setTextFill(Color.GRAY);
        } else {
            player1Name.setTextFill(Color.GRAY);
            player2Name.setTextFill(Color.WHITE);
            player1Gulden.setTextFill(Color.GRAY);
            player2Gulden.setTextFill(Color.WHITE);
        }
    }



    private int convertGridToListIdx(int i, int j){
        return i*4 + j;
    }
    private int[] convertListIdxToGrid(int listIndex) {
        int rowIndex = listIndex / 4;
        int colIndex = listIndex % 4;
        return new int[]{rowIndex, colIndex};
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

    void updateActiveDeck(Player pl){
        leftDeck.getChildren().clear();

        for (int i = 0; i < sourceViews.length; i++) {
            sourceViews[i] = new ProductCard("assets/Basic.png");
            leftDeck.getChildren().add(sourceViews[i]);
        }

        List<Resource> activeDeckPlayer = pl.getActiveDeck();
        for(int i = 0; i < activeDeckPlayer.size(); i++) {
            Resource resource = activeDeckPlayer.get(i);
            if(resource != null) {
                String name = resource.getName();
                sourceViews[i] = CreatureCard.getCreatureCard(name);
                leftDeck.getChildren().remove(i);
                leftDeck.getChildren().add(i,sourceViews[i]);
            }
        }
    }

    void updateLadang(Player pl) {
        ladangDeck.getChildren().clear();

        for (int i = 0; i < destinationViews.length; i++) {
            destinationViews[i] = new EmptyCard();
            ladangDeck.getChildren().add(destinationViews[i]);
        }

        Grid<Resource> ladangPlayer = pl.getLadang();
        ladangPlayer.forEachActive((a) -> {
            Resource currentElement = ladangPlayer.getElement(a);
            String name = currentElement.getName();

            // Set Destination Views
            int gridIDX = convertGridToListIdx(a.getCol(),a.getRow());
            destinationViews[gridIDX] = CreatureCard.getCreatureCard(name);

            // Update Deck
            ladangDeck.getChildren().remove(gridIDX);
            ladangDeck.getChildren().add(gridIDX,destinationViews[gridIDX]);
        });
    }

    @FXML
    void onMyFieldClick(ActionEvent event){
        for (int i = 0; i < destinationViews.length; i++) {
            destinationViews[i] = new ProductCard("assets/Basic.png");
            ladangDeck.getChildren().add(destinationViews[i]);
        }

        /**
         * Set Ladang
         * */
        updateLadang(this.state.getCurrentPlayer());
    }

    @FXML
    void onEnemyFieldClick(ActionEvent event){
        ladangDeck.getChildren().clear();
        for (int i = 0; i < destinationViews.length; i++) {
            destinationViews[i] = new ProductCard("assets/Basic.png");
            ladangDeck.getChildren().add(destinationViews[i]);
        }

        /**
         * Set Ladang
         * */
        updateLadang(this.state.getNextPlayer());
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
                updateLadang(this.state.getNextPlayer());
                updateActiveDeck(this.state.getNextPlayer());
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
