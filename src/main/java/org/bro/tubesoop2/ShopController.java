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
    private TilePane tilePane, sellTilePane;

    private List<Item> buyList = new ArrayList<>();

    private List<Item> inventory = new ArrayList<>();

    private List<Item> itemToSell = new ArrayList<>();

    @FXML
    void initialize() {
        for (int i = 0; i < 10; i++) {
            HBox itemBox = createDummyItemBox(i,(i+1)*100,i);
            Item inventoryItem = new Item(String.valueOf(i),i);
            inventory.add(inventoryItem);
            sellTilePane.getChildren().add(itemBox);
        }

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            updateBuyButtonText(newTab);
        });

        updateBuyButtonText(tabPane.getSelectionModel().getSelectedItem());

        for (int i = 0; i < sellTilePane.getChildren().size(); i++) {
            HBox itemBox = (HBox) sellTilePane.getChildren().get(i);
            Item item = new Item(itemBox.getId(), 5);
            itemBox.setOnMouseClicked(event -> {
                promptForQuantity(item, "sell");
            });
        }

        for (int i = 0; i < tilePane.getChildren().size(); i++) {
            HBox itemBox = (HBox) tilePane.getChildren().get(i);
            Item item = new Item(itemBox.getId(), 5);
            itemBox.setOnMouseClicked(event -> {
                promptForQuantity(item, "buy");
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

    private void promptForQuantity(Item item, String txt) {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Enter Quantity");
        dialog.setHeaderText("How many " + item.getName() + " do you want to "+txt+"?");

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
                    return null; // Return null to prevent adding invalid quantity
                }

                try {
                    int quantity = Integer.parseInt(textField.getText());
                    if (quantity > item.getQuantity()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Not enough items left!");
                        alert.showAndWait();
                        return null; // Return null to prevent adding invalid quantity
                    } else if(txt=="sell"){
                        item.setQuantity(quantity);
                        addToSellList(item);
                    }
                    else if(txt=="buy"){
                        item.setQuantity(quantity);
                        addToBuyList(item);
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

    private HBox createDummyItemBox(int id, int price, int quantity) {
            HBox hBox = new HBox();
            hBox.setId(String.valueOf(id));
            hBox.setPrefHeight(107.0);
            hBox.setPrefWidth(167.0);
            hBox.setSpacing(15.0);
            hBox.getStyleClass().add("card");
            hBox.getStylesheets().add("file:/style.css");

            ImageView imageView = new ImageView();
            imageView.setId("image" + id);
            imageView.setFitHeight(110.0);
            imageView.setFitWidth(77.0);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setImage(new Image("file:/assets/test.png"));

            VBox labelsVBox = new VBox();
            labelsVBox.setPrefHeight(200.0);
            labelsVBox.setPrefWidth(100.0);
            labelsVBox.setSpacing(10.0);

            Label priceLabel = new Label("Price");
            priceLabel.setFont(new Font("Poppins SemiBold", 14.0));

            Label priceValueLabel = new Label(String.valueOf(price));
            priceValueLabel.setId("price"+id);
            priceValueLabel.setFont(new Font("Poppins SemiBold", 14.0));

            labelsVBox.getChildren().addAll(priceLabel, priceValueLabel);

            Label stockLabel = new Label("Stock");
            stockLabel.setFont(new Font("Poppins SemiBold", 14.0));

            Label stockValueLabel = new Label(String.valueOf(quantity));
            stockValueLabel.setId("stock"+id);
            stockValueLabel.setFont(new Font("Poppins SemiBold", 14.0));

            labelsVBox.getChildren().addAll(stockLabel, stockValueLabel);

            hBox.getChildren().addAll(imageView, labelsVBox);

            hBox.setPadding(new Insets(10.0));

            return hBox;
    }

    private void addToBuyList(Item item) {
        buyList.add(item);
        System.out.println("Added " + item.getQuantity() + " " + item.getName() + " to buy list.");
    }

    private void addToSellList(Item item) {
        itemToSell.add(item);
        System.out.println("Added " + item.getQuantity() + " " + item.getName() + " to sell list.");
    }

    @FXML
    void onBuy(ActionEvent event) {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab == buyTab) {
            buyItems();
        } else if (selectedTab == sellTab) {
           sellItems();
        }
    }

    private void buyItems() {
        System.out.println("Buying items:");
        for (Item item : buyList) {
            System.out.println(item.getQuantity() + " " + item.getName());
        }
        buyList.clear();
    }

    private void sellItems() {
        System.out.println("Selling items:");
        for (Item item : itemToSell) {
            System.out.println(item.getQuantity() + " " + item.getName());
        }
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

        public Item(String name, int quantity) {
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
