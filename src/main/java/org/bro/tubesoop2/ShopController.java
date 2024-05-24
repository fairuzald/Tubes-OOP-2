package org.bro.tubesoop2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import org.bro.tubesoop2.action.Action;
import org.bro.tubesoop2.player.Player;
import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.quantifiable.Quantifiable;
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.state.GameState;
import org.bro.tubesoop2.toko.Toko;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.geometry.Insets;

public class ShopController {
    @FXML
    private Button backButton, buyButton;

    @FXML
    private Tab buyTab, sellTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private TilePane buyPane, sellTilePane;

    private List<Tuple<Integer, Integer>> buyList = new ArrayList<>();

    private List<Tuple<Resource, Integer>> itemToSell = new ArrayList<>();

    public static Action<List<Tuple<Integer, Integer>>> onBuy = new Action<>();

    public static Action<List<Tuple<Resource, Integer>>> onSell = new Action<>();

    private static GameState state;

    public static void setState(GameState pl) {
        state = pl;
    };

    public static int getInventoryIdx(List<Quantifiable<Resource>> list,Resource rsc) throws Exception {
        for(int i=0;i<list.size();i++){
            if(list.get(i).getValue().getName().equals(rsc.getName())){
                return i;
            }
        }
        throw new Exception();
    }

    public static List<Quantifiable<Resource>> getInventory(boolean soldable){
        List<Resource> in = state.getCurrentPlayer().getActiveDeck();
        List<Quantifiable<Resource>> temp = new ArrayList<>();

        System.out.println("inventory"+in);
        if(soldable) {
            for (Resource rsc: in) {
                if(rsc instanceof Product) {
                    try {
                        Quantifiable<Resource> item = temp.get(getInventoryIdx(temp,rsc));
                        item.increment();
                    } catch (Exception e) {
                        temp.add(new Quantifiable<>(rsc,1));
                    }
                }
            }
        } else {
            for (Resource rsc: in) {
                try {
                    Quantifiable<Resource> item = temp.get(getInventoryIdx(temp, rsc));
                    item.increment();
                } catch (Exception e) {
                    temp.add(new Quantifiable<>(rsc, 1));
                }
            }
        }

        return temp;
    }

    public static Toko getTokoStatic(){
        return state.getToko();
    }


    @FXML
    void initialize() {
        System.out.println("Aku terdefinisi qq!!");
        System.out.println(state.getCurrentPlayer());
        System.out.println(state.getCurrentPlayer().getActiveDeck());

        List<Quantifiable<Resource>> inventory = new ArrayList<>(getInventory(false));
        for(Quantifiable<Resource> qrsc:inventory){
            if(qrsc.getValue() instanceof Product){
                HBox itemBox = createDummyItemBox(qrsc.getValue().getName(),((Product) qrsc.getValue()).getPrice(), qrsc.getQuantity(), "assets/Produk/"+qrsc.getValue().getName()+".png");
                sellTilePane.getChildren().add(itemBox);
            }
        }

        List<Quantifiable<Resource>> stock_toko = new ArrayList<>(getTokoStatic().getStock());
        for(Quantifiable<Resource> rsc:stock_toko){
            if(rsc.getValue() instanceof Product){
                HBox itemBox = createDummyItemBox(rsc.getValue().getName(),((Product) rsc.getValue()).getPrice(),rsc.getQuantity(), "assets/Produk/"+rsc.getValue().getName()+".png");
                buyPane.getChildren().add(itemBox);
            }

        }
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            updateBuyButtonText(newTab);
        });

        updateBuyButtonText(tabPane.getSelectionModel().getSelectedItem());

        for (int i = 0; i < sellTilePane.getChildren().size(); i++) {
            HBox itemBox = (HBox) sellTilePane.getChildren().get(i);
            final int temp=i;
            itemBox.setOnMouseClicked(event -> {
                promptForQuantity(temp, "sell");
            });
        }

        for (int i = 0; i < buyPane.getChildren().size(); i++) {
            HBox itemBox = (HBox) buyPane.getChildren().get(i);
            final int temp = i;
            itemBox.setOnMouseClicked(event -> {
                promptForQuantity(temp, "buy");
            });
        }
    }

    private void updateBuyButtonText(Tab selectedTab) {
        if (selectedTab == buyTab) {
            buyButton.setText("Buy Item");
        } else if (selectedTab == sellTab) {
            buyButton.setText("Sell Item");
        }
    }

    private void promptForQuantity(int index, String txt) {
        Dialog<Integer> dialog = new Dialog<>();
        Quantifiable<Resource> rsc = getTokoStatic().getStock().get(index);
        dialog.setTitle("Enter Quantity");
        dialog.setHeaderText("How many " + rsc.getValue().getName() + " do you want to "+txt+"?");

        Label label = new Label("Quantity:");
        TextField textField = new TextField();
        textField.setPromptText("Enter quantity");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(new VBox(10, label, textField));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String inputText = textField.getText();
                if (inputText.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a quantity!");
                    alert.showAndWait();
                    return null;
                }

                try {
                    int quantity = Integer.parseInt(textField.getText());
                    if (quantity > rsc.getQuantity()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Not enough items left!");
                        alert.showAndWait();
                        return null; // Return null to prevent adding invalid quantity
                    } else if(txt.equals("sell")){
                        addToSellList(index,quantity);
                    }
                    else if(txt.equals("buy")){
                        addToBuyList(index, quantity);
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            return null;
        });

        dialog.showAndWait();
    }

    private HBox createDummyItemBox(String id, int price, int quantity, String imagePath) {
        HBox hBox = new HBox();
        hBox.setId(String.valueOf(id));
        hBox.setPrefHeight(107.0);
        hBox.setPrefWidth(167.0);
        hBox.setSpacing(15.0);

        // Apply the styles directly to the HBox
        hBox.setStyle("-fx-background-radius: 6px; " +
                "-fx-border-color: #135D66; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 6px; " +
                "-fx-border-style: solid; " +
                "-fx-background-color: #E3FEF7;");

        ImageView imageView = new ImageView();
        imageView.setId("image" + id);
        imageView.setFitHeight(110.0);
        imageView.setFitWidth(77.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        Image myImage = new Image(getClass().getResourceAsStream(imagePath));
        imageView.setImage(myImage);

        VBox labelsVBox = new VBox();
        labelsVBox.setPrefHeight(200.0);
        labelsVBox.setPrefWidth(100.0);
        labelsVBox.setSpacing(10.0);

        Label priceLabel = new Label("Price");
        priceLabel.setFont(new Font("Poppins SemiBold", 14.0));

        Label priceValueLabel = new Label(String.valueOf(price));
        priceValueLabel.setId("price" + id);
        priceValueLabel.setFont(new Font("Poppins SemiBold", 14.0));

        labelsVBox.getChildren().addAll(priceLabel, priceValueLabel);

        Label stockLabel = new Label("Stock");
        stockLabel.setFont(new Font("Poppins SemiBold", 14.0));

        Label stockValueLabel = new Label(String.valueOf(quantity));
        stockValueLabel.setId("stock" + id);
        stockValueLabel.setFont(new Font("Poppins SemiBold", 14.0));

        labelsVBox.getChildren().addAll(stockLabel, stockValueLabel);

        hBox.getChildren().addAll(imageView, labelsVBox);

        hBox.setPadding(new Insets(10.0));

        return hBox;
    }

    private void addToBuyList(Integer idx, Integer quantity) {
        Tuple<Integer, Integer> t = new Tuple<>(idx,quantity);
        buyList.add(t);
    }

    private void addToSellList(Integer idx, Integer quantity) {
        Tuple<Resource, Integer> t = new Tuple<>(getInventory(true).get(idx).getValue(),quantity);
        itemToSell.add(t);
    }

    @FXML
    void onBuy(ActionEvent event) {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab == buyTab) {
            onBuy.Notify(this.buyList);

        } else if (selectedTab == sellTab) {
            onSell.Notify(this.itemToSell);
        }
        Stage stage = (Stage) buyButton.getScene().getWindow();
        stage.close();
        ShopController.setShopWindowOpen(false);
    }

    private void buyItems() {
        System.out.println("Buying items:");
        buyList.clear();
    }

    private void sellItems() {
        System.out.println("Selling items:");
    }

    @FXML
    void onBack(ActionEvent event) {
        setShopWindowOpen(false);
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }

    public static boolean isShopWindowOpen() {
        return shopWindowOpen;
    }

    public static void setShopWindowOpen(boolean open) {
        shopWindowOpen = open;
    }

    private static boolean shopWindowOpen = false;

    private static class Item {
        private String name;
        private int quantity;

        public  Item(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }


    }

}
