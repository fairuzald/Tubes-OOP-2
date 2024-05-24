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
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.bro.tubesoop2.animal.Animal;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.grid.Grid;
import org.bro.tubesoop2.grid.Location;
import org.bro.tubesoop2.player.Player;
import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.state.GameState;
import org.bro.tubesoop2.state.StateLoader;
import org.bro.tubesoop2.state.TextLoader;

public class MainController {
    GameState state = new GameState();
    StateLoader loader = new StateLoader();
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
        
        // Remove all
        for (int i = 0; i < destinationViews.length; i++) {
            destinationViews[i] = new EmptyCard();
            ladangDeck.getChildren().add(destinationViews[i]);
        }
        for (int i = 0; i < sourceViews.length; i++) {
            sourceViews[i] = new EmptyCard();
            leftDeck.getChildren().add(sourceViews[i]);
        }

        





        EmptyCard.onDrop.AddListener(tup -> {
            Resource rsc = tup.getFirst();
            Integer index = tup.getSecond();
            int[] gridPosition = convertListIdxToGrid(index);
            int row = gridPosition[0];
            int col = gridPosition[1];
            Location lct = new Location(row, col);
            state.getCurrentPlayer().addLadang(rsc, lct);
        });

        CreatureCard.onMakan.AddListener(paths->{
            String sourcePath = paths.getSecond();
            Integer index = paths.getFirst();
            Product p = new Product(sourcePath,0,0);
            int[] gridPosition = convertListIdxToGrid(index);
            int row = gridPosition[0];
            int col = gridPosition[1];

            List<Resource> rscs = state.getCurrentPlayer().getActiveDeck();
            Resource animal = state.getCurrentPlayer().getLadang().getElement(row,col);
            for (Resource rsc : rscs) {
                if(rsc instanceof Product){
                    if(rsc.getName()==p.getName()){
                        p = (Product) rsc;
                    }
                }
            }
            if (animal instanceof Animal) {
                try {
                    ((Animal) animal).eat(p);
                } catch (Exception e) {
                    // Display an error message dialog
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("An error occurred while processing the action.");
                    alert.setContentText("Details: " + e.getMessage());
                    alert.showAndWait();
                }
            }

        });
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
            loader.setPath(folderDir, "gamestate.txt", "player1.txt", "player2.txt")
                    .setPlugin(new TextLoader())
                    .loadState(state);
            updateGUI(state);
        });
     
        // Show detail
        CreatureCard.onCreatureCardClicked.AddListener(creature -> {
            onItemClick(null, (Resource)creature.getResource());            
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

        
        initActiveDeck();
        initLadang();
    }

    /**
     * Set Active Deck
     * */
    void initActiveDeck(){
        List<Resource> activeDeckPlayer = this.state.getCurrentPlayer().getActiveDeck();
        for(int i = 0; i < activeDeckPlayer.size(); i++) {
            Resource resource = activeDeckPlayer.get(i);
            if(resource != null) {
                sourceViews[i] = Card.createCard(resource);
                leftDeck.getChildren().set(i,sourceViews[i]);
            }
        }
    }

    /**
     * Set Ladang
     * */
    // Iterasi grid aktif
    void initLadang(){
        Grid<Resource> ladangPlayer = this.state.getCurrentPlayer().getLadang();
        ladangPlayer.forEachActive((a) -> {
            // Set Destination Views
            int gridIDX = convertGridToListIdx(a.getCol(),a.getRow());
            // destinationViews[gridIDX] = CreatureCard.getCreatureCard(name);

            // Update Deck
            ladangDeck.getChildren().remove(gridIDX);
            ladangDeck.getChildren().add(gridIDX,destinationViews[gridIDX]);
        });
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
    private void onItemClick(MouseEvent event, Resource c) {
        if (!DetailController.isDetailOpen()) {
            try {
                Creature creature = (Creature) c;
                String itemName = creature.getFormattedName();
                String[] activeItems = creature.getItemsActive().stream().map(item -> item.getName()).toArray(String[]::new);
                String value = Integer.toString(creature.getUmurOrBerat());
                String label = creature instanceof Animal ? "Berat" : "Umur";

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detail.fxml"));
                Parent root = fxmlLoader.load();
                Stage detailStage = new Stage();

                DetailController controller = fxmlLoader.getController();
                controller.updateDetails(itemName, activeItems, value, label);

                detailStage.setTitle(creature.getName());
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
            // sourceViews[i] = new ProductCard("assets/Basic.png");
            leftDeck.getChildren().add(sourceViews[i]);
        }

        List<Resource> activeDeckPlayer = pl.getActiveDeck();
        for(int i = 0; i < activeDeckPlayer.size(); i++) {
            Resource resource = activeDeckPlayer.get(i);
            if(resource != null) {
                String name = resource.getName();
                // sourceViews[i] = CreatureCard.getCreatureCard(name);
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
            // destinationViews[gridIDX] = CreatureCard.getCreatureCard(name);

            // Update Deck
            ladangDeck.getChildren().remove(gridIDX);
            ladangDeck.getChildren().add(gridIDX,destinationViews[gridIDX]);
        });
    }

    @FXML
    void onMyFieldClick(ActionEvent event){
        for (int i = 0; i < destinationViews.length; i++) {
            // destinationViews[i] = new ProductCard("assets/Basic.png");
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
            // destinationViews[i] = new ProductCard("assets/Basic.png");
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
