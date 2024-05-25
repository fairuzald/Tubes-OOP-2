package org.bro.tubesoop2;

import javafx.scene.control.Alert;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.TilePane;
import org.bro.tubesoop2.action.Action;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.item.*;
import org.bro.tubesoop2.plant.Plant;
import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.resource.Resource;

public class CreatureCard extends Card {

    public static Action<Integer> onCreatureCardClicked = new Action<>();
    public static Action<Item> onEnemyClicked = new Action<>();
    public static Action<Tuple<Item, Resource>> onEnemyDestroy = new Action<>();

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
    public static Action<Tuple<Integer, Item>> onItemGiven = new Action<>();
    public static Action<String> onItemCancel = new Action<String>();

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
                System.out.println("MASOK");
                TilePane parentContainer = (TilePane) getParent();
                int index = parentContainer.getChildren().indexOf(this);
                System.out.println(this.getResource().getClass());
                if(((ProductCard) source).getResource() instanceof  Product){
                    System.out.println("MASOK2");
                    onMakan.Notify(new Tuple<>(index, (Product) ((ProductCard) source).getResource()));
                }

            }
        }
        else if (source instanceof ItemCard) {
            System.out.println("hello world!");
            if (((ItemCard) source).getResource() instanceof Delay) {
                onItemCancel.Notify("Delay");
            }  else if (((ItemCard) source).getResource() instanceof Destroy) {
                onItemCancel.Notify("Destroy");
            } else if (((ItemCard) source).getResource() instanceof InstantHarvest) {

            } else {
                if(getParent() instanceof TilePane){
                    TilePane parentContainer = (TilePane) getParent();
                    int index = parentContainer.getChildren().indexOf(this);
                    if(((ItemCard) source).getResource() instanceof Item){
                        System.out.println("MASOK2");
                        onItemGiven.Notify(new Tuple<>(index, (Item) ((ItemCard) source).getResource()));
                    }
                }
            }


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

    @Override
    public void dropEnemy(Dragboard db, Object src) {
        if (src instanceof ItemCard) {
            if ((((ItemCard) src).getResource() instanceof Delay)) {
                ((Delay) ((ItemCard) src).getResource()).consumedBy((Creature) this.getResource());
                Item rsc = ((Item)((ItemCard) src).getResource());
                onEnemyClicked.Notify(rsc);

            }

            if ((((ItemCard) src).getResource() instanceof Destroy)) {
                ((Destroy) ((ItemCard) src).getResource()).consumedBy((Creature) this.getResource());
                Item rsc = ((Item)((ItemCard) src).getResource());

                Tuple<Item, Resource> tup = new Tuple<Item, Resource>(rsc, this.getResource());
                onEnemyDestroy.Notify(tup);
            }


        }


    }
}
