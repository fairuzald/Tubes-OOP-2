package org.bro.tubesoop2;

import javafx.scene.control.Alert;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.TilePane;
import org.bro.tubesoop2.action.Action;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.plant.Plant;
import org.bro.tubesoop2.product.Product;

public class CreatureCard extends Card {

    public static Action<Integer> onCreatureCardClicked = new Action<>();
    
    public CreatureCard(Creature c) {
        super(c, c instanceof Plant ? "assets/Tanaman/"+c.getName()+".png" : "assets/Animal/"+c.getName()+".png");
        setOnMouseClicked(e -> {
            if (this.isDragable()) {
                int locationIndex = ((TilePane) getParent()).getChildren().indexOf(this);
                onCreatureCardClicked.Notify(locationIndex);
            }
        });
    }

    public static Action<Tuple<Integer, Product>> onMakan = new Action<>();

    @Override
    public void dragDoneAction() {
        System.out.println("CreatureCard drag done.");
    }


    @Override
    protected void handleDrop(Dragboard dragboard, Object source) {
        if (source instanceof CreatureCard) {
            System.out.println("CreatureCard cannot be replaced by another CreatureCard.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot replace CreatureCard with another CreatureCard.");
            alert.showAndWait();
            return;
        }
        else if(source instanceof ProductCard){
            String imagePath = dragboard.getString();
            if (getParent() instanceof TilePane) {
                TilePane parentContainer = (TilePane) getParent();
                int index = parentContainer.getChildren().indexOf(this);
                if(this.getResource() instanceof  Product){
                onMakan.Notify(new Tuple<>(index, (Product) this.getResource()));
                }

            }
        }
        else if (source instanceof ItemCard) {
            System.out.println("CreatureCard cannot be replaced by ItemCard or ProductCard.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot replace CreatureCard with ItemCard or ProductCard.");
            alert.showAndWait();
            return;
            // No change in image or class
        } else {
            System.out.println("CreatureCard cannot be replaced by this type.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot replace CreatureCard with this type.");
            alert.showAndWait();
            return;
        }
    }
}
