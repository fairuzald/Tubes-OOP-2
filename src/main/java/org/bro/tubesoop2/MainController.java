package org.bro.tubesoop2;
import javafx.application.Platform;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
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
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.bro.tubesoop2.animal.Animal;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.countdowntimer.CountdownTimer;
import org.bro.tubesoop2.grid.Grid;
import org.bro.tubesoop2.grid.Location;
import org.bro.tubesoop2.plant.Plant;
import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.item.Protect;
import org.bro.tubesoop2.item.Trap;
import org.bro.tubesoop2.player.Player;
import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.quantifiable.Quantifiable;
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.resource.ResourceFactory;
import org.bro.tubesoop2.seranganberuang.SeranganBeruang;
import org.bro.tubesoop2.state.GameState;
import org.bro.tubesoop2.state.StateLoader;
import org.bro.tubesoop2.state.TextLoader;
import org.bro.tubesoop2.toko.Toko;
import org.bro.tubesoop2.toko.TokoException;
import org.bro.tubesoop2.utils.Utils;

public class MainController {
    GameState state = new GameState();
    StateLoader loader = new StateLoader();
    private int activeDeckNum = 6;
    RandomController randomController = new RandomController();

    @FXML
    private Label player1Name, player2Name, player1Gulden, player2Gulden, activeDeck, turn, timerLabel;

    @FXML
    private Button shopButton, loadButton, myFieldButton, enemyFieldButton, saveButton, pluginButton, nextButton;

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



        ShopController.onBuy.AddListener(arrToBuy ->{
            try{
                Toko toko  = state.getToko();

                for(Tuple<Integer, Integer> src:arrToBuy){
                    toko.buy(state.getCurrentPlayer(), src.getFirst(), src.getSecond());
                }

            }catch (TokoException e){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred while processing the action.");
                alert.setContentText("Details: " + e.getMessage());
                alert.showAndWait();
            } catch (Exception e){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred while processing the action.");
                alert.setContentText("Details: " + e.getMessage());
                alert.showAndWait();
            }finally {
                updateGUI();
            }
        });

        ShopController.onSell.AddListener(arrToSell ->{
            try{
                Toko toko  = state.getToko();

                for(Tuple<Resource, Integer> src:arrToSell){
                    toko.sell(state.getCurrentPlayer(), src.getFirst(), src.getSecond());
                }

                ShopController.setState(state);

            }catch (TokoException e){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred while processing the action.");
                alert.setContentText("Details: " + e.getMessage());
                alert.showAndWait();
            }finally {
                updateGUI();
            }

        });

        EmptyCard.onDrop.AddListener(tup -> {
            Integer indexFrom = tup.getFirst();
            Tuple<Resource, Integer> tup2 = tup.getSecond();
            Resource rsc = tup2.getFirst();
            Integer index = tup2.getSecond();
            int[] gridPosition = convertListIdxToGrid(index);
            int row = gridPosition[0];
            int col = gridPosition[1];
            Location lct = new Location(row, col);
            System.out.print(indexFrom);
            if(indexFrom ==-1){
                state.getCurrentPlayer().putLadang(rsc, lct);
            }else{
                int[] gridPositionFrom = convertListIdxToGrid(indexFrom);
                int row2 = gridPositionFrom[0];
                int col2 = gridPositionFrom[1];
                Location rlct = new Location(row2,col2);
                state.getCurrentPlayer().removeLadang(rlct);
                state.getCurrentPlayer().putLadang(rsc, lct);
            }
        });

        CreatureCard.onMakan.AddListener(paths->{
            System.out.println("WOY");

            Product p = paths.getSecond();
            Integer index = paths.getFirst();
            int[] gridPosition = convertListIdxToGrid(index);
            int row = gridPosition[0];
            int col = gridPosition[1];

            Resource animal = state.getCurrentPlayer().getLadang().getElement(row,col);

            if (animal instanceof Animal) {
                try {
                    System.out.println(animal);
                    System.out.println(p);
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

        CreatureCard.onItemGiven.AddListener(paths -> {
            Item item = paths.getSecond();
            Integer index = paths.getFirst();
            
            int[] gridPosition = convertListIdxToGrid(index);
            int row = gridPosition[0];
            int col = gridPosition[1];

            Resource creature = state.getCurrentPlayer().getLadang().getElement(row,col);
            try{
                System.out.println("mmmsskkk");
                item.consumedBy((Creature)creature);
                // updateGUI();
            }catch (Exception e) {
                // Display an error message dialog
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred while processing the action.");
                alert.setContentText("Details: " + e.getMessage());
                alert.showAndWait();
            }
            
        });

        RandomController.onNextDone.AddListener(r -> {
            int length = RandomController.selectedViews.size();
            for (int i = 0; i < length; i++) {
                String current_absolute_path = RandomController.selectedViews.get(i).getImage().getUrl();
                System.out.println(current_absolute_path);
                String relative_path_from_project = Utils.getRelativePathFromProject(current_absolute_path);
                System.out.println(relative_path_from_project);
                String key = Utils.toResourceFactoryKeys(relative_path_from_project);
                state.getCurrentPlayer().getActiveDeck().add(state.createResource(key));
            }
            // Increment plant age
            state.getPlayer1().getLadang().forEachActive(l -> {
                Creature c = (Creature) state.getCurrentPlayer().getLadang().getElement(l);
                if(c instanceof Plant){
                    ((Plant) c).addAge(2);
                }
            });
            state.getPlayer2().getLadang().forEachActive(l -> {
                Creature c = (Creature) state.getCurrentPlayer().getLadang().getElement(l);
                if(c instanceof Plant){
                    ((Plant) c).addAge(2);
                }
            });

            updateGUI();
            System.out.println(state.getCurrentPlayer().getLadang().getCountFilled());
            seranganBeruangHandler(state);

            // win check
            Player winningPlayer = state.tryGetWinner();
            if(winningPlayer != null) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(winningPlayer.getName() + " Wins!");
                alert.showAndWait();
            }
        });

        SaveController.onSaveValid.AddListener(folderDir -> {
            loader.setPath(folderDir, "gamestate_.txt", "player1_.txt", "player2_.txt")
                    .setPlugin(new TextLoader())
                    .saveState(state);
            updateGUI();
        });

        LoadController.onLoadValid.AddListener(folderDir -> {
            loader.setPath(folderDir, "gamestate.txt", "player1.txt", "player2.txt")
                    .setPlugin(new TextLoader())
                    .loadState(state);
            updateGUI();
            ShopController.setState(state);
        });
     
        // Show detail
        CreatureCard.onCreatureCardClicked.AddListener(locationIndex -> {
            // onItemClick(null, (Resource)creature.getResource());
            Card d = (Card)ladangDeck.getChildren().get(locationIndex);
            Creature creature = (Creature) d.getResource();
            onItemClick(null, creature, locationIndex);
        });
        
        DetailController.onHarvestClicked.AddListener(locationIndex -> {
            try{
                Card c = (Card)ladangDeck.getChildren().get(locationIndex);
                Creature creature = (Creature) c.getResource();
                int[] pos = convertListIdxToGrid(locationIndex);

                if(state.getCurrentPlayer().isActiveDeckFull()) throw new IllegalStateException("Active deck is full!");
                Product product = creature.harvest();
                state.getCurrentPlayer().addToDeck(product);

                state.getCurrentPlayer().getLadang().pop(pos[0], pos[1]);
                updateGUI();
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
            }
        });
    }

    void updateGUI(){
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
        player1Gulden.setText(state.getPlayer1().getGulden().toString());
        player2Gulden.setText(state.getPlayer2().getGulden().toString());
        updateActiveDeck(state.getCurrentPlayer());
        updateLadang(state.getCurrentPlayer());
    }



    void resetActiveDeckViews() {
        state.getCurrentPlayer().compactActiveDeck();

        sourceViews = new DraggableItem[6];
        for (int i = 0; i < 6; i++) {
            sourceViews[i] = new EmptyCard();
            leftDeck.getChildren().set(i, sourceViews[i]);
        }
    }


    private int convertGridToListIdx(int i, int j){
        return i*5 + j;
    }
    private int[] convertListIdxToGrid(int listIndex) {
        int rowIndex = listIndex / 5;
        int colIndex = listIndex % 5;
        return new int[]{rowIndex, colIndex};
    }


    @FXML
    private void onItemClick(MouseEvent event, Resource c, int locationIndex) {
        if (!DetailController.isDetailOpen()) {
            try {
                Creature creature = (Creature) c;
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detail.fxml"));
                Parent root = fxmlLoader.load();
                Stage detailStage = new Stage();

                DetailController controller = fxmlLoader.getController();
                controller.updateDetails(creature, locationIndex);

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

    /**
     * Set Active Deck
     * */
    void updateActiveDeck(Player pl){
        leftDeck.getChildren().clear();
        for (int i = 0; i < 6; i++) {
            sourceViews[i] = new EmptyCard();
            leftDeck.getChildren().add(sourceViews[i]);
        }

        List<Resource> activeDeckPlayer = pl.getActiveDeck();
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
    void updateLadang(Player pl) {
        ladangDeck.getChildren().clear();

        for (int i = 0; i < destinationViews.length; i++) {
            destinationViews[i] = new EmptyCard();
            ladangDeck.getChildren().add(destinationViews[i]);
        }

        Grid<Resource> ladangPlayer = pl.getLadang();
        ladangPlayer.forEachActive((a) -> {
            Resource currentElement = ladangPlayer.getElement(a);

            // Set Destination Views
            int gridIDX = convertGridToListIdx(a.getCol(),a.getRow());
             destinationViews[gridIDX] = Card.createCard(currentElement);

            // Update Deck
            ladangDeck.getChildren().set(gridIDX,destinationViews[gridIDX]);
        });

    }

    @FXML
    void onMyFieldClick(ActionEvent event){
        /**
         * Set Ladang
         * */
        updateLadang(this.state.getCurrentPlayer());
    }

    @FXML
    void onEnemyFieldClick(ActionEvent event){
        /**
         * Set Ladang
         * */
        updateLadang(this.state.getNextPlayer());
        ladangDeck.getChildren().forEach((a) -> ((DraggableItem) a).setDragState(false));
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

        state.NextTurn();
        resetActiveDeckViews();
        updateGUI();

        RandomController.maximumCardsCanBeSelected = 6 - state.getCurrentPlayer().getActiveDeck().size();


        if (RandomController.maximumCardsCanBeSelected == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No new card");
            alert.setContentText("You have 6 active deck!");
            alert.showAndWait();
            return;
        }

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
                updateLadang(this.state.getCurrentPlayer());
                updateActiveDeck(this.state.getCurrentPlayer());
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
                ShopController.setState(state);
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


    private void applyRedBorder(ImageView imageView) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(1);
        colorAdjust.setSaturation(1);
        imageView.setEffect(colorAdjust);
    }

    private void disableRedBorder(ImageView imageView) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0);
        colorAdjust.setSaturation(0);
        imageView.setEffect(colorAdjust);
    }

    @FXML
    void seranganBeruangHandler(GameState state){
        Random random = new Random();
        if(true){
            shopButton.setDisable(true);
            loadButton.setDisable(true);
            myFieldButton.setDisable(true);
            enemyFieldButton.setDisable(true);
            saveButton.setDisable(true);
            pluginButton.setDisable(true);
            nextButton.setDisable(true);

            Thread timerThread = new Thread(() -> {
            SeranganBeruang sb = new SeranganBeruang();
            List <Integer> affected = sb.generateAffectedIndex();
            for(int i = 0; i < affected.size(); i++){
                int idx = affected.get(i);
                System.out.println(idx);
                applyRedBorder(destinationViews[idx]);
            }
            System.out.println("Affected: " + affected);
            CountdownTimer countdownTimer = new CountdownTimer(5);
            Platform.runLater(() -> timerLabel.setVisible(true));
            countdownTimer.start();
            while (!countdownTimer.isTimeUp()) {
                try {

                    Thread.sleep(100);
                    String currtime = Integer.toString(countdownTimer.getTime()/1000) + "," + Integer.toString((countdownTimer.getTime()%1000)/100); ;
                    Platform.runLater(() -> {timerLabel.setText(currtime);}
                    );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> timerLabel.setVisible(false));
            System.out.println("test");
            System.out.println("Affected: " + affected);

            for(int i = 0; i < affected.size(); i++){
                int idx = affected.get(i);

                Platform.runLater(() -> {
                    killCreatureAt(idx);
                    updateLadang(state.getCurrentPlayer());
                });
            }
            Platform.runLater(() -> {
                shopButton.setDisable(false);
                loadButton.setDisable(false);
                myFieldButton.setDisable(false);
                enemyFieldButton.setDisable(false);
                saveButton.setDisable(false);
                pluginButton.setDisable(false);
                nextButton.setDisable(false);
            });

           });


           timerThread.start();
       }
   }
    public void killCreatureAt(int i){
        
        int[] gridPosition = convertListIdxToGrid(i);
        boolean hasProtectCard = false;
        boolean hasTrap = false;
        Creature creature = (Creature) state.getCurrentPlayer().getLadang().getElement(gridPosition[0], gridPosition[1]);
        if(creature == null) return;
        System.out.println(creature+"uhuy");
        for (Item item : creature.getItemsActive()) {
            if (item instanceof Protect) {
                hasProtectCard = true;
               
            }
            if(item instanceof Trap){
                hasTrap = true;
            }
        }

        if(hasProtectCard){
            return;
        }
        if(hasTrap){
            state.getCurrentPlayer().addToDeck(state.createResource("BERUANG"));
//            updateActiveDeck();
            return;
        }
        try{
            state.getCurrentPlayer().getLadang().pop(gridPosition[0], gridPosition[1]);
            // ladangDeck.getChildren().remove(i);
            // ladangDeck.getChildren().add(i,destinationViews[i]);

        }catch(IllegalStateException e){
            // System.out.println("failed at" + " " + row + " "+ col) ;
        }
    }

//}
}
