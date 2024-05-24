package org.bro.tubesoop2;

import javafx.scene.input.Dragboard;
import javafx.scene.layout.TilePane;
import org.bro.tubesoop2.action.Action;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.plant.Plant;
import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.resource.Resource;

import java.util.Objects;

public class CreatureCard extends Card {

    public CreatureCard(Creature c) {
        super(c, c instanceof Plant ? "assets/Plant/"+c.getName()+".png" : "assets/Animal/"+c.getName()+".png");
    }

    public static Action<Tuple<Integer, String>> onMakan = new Action<>();

    @Override
    public void dragDoneAction() {
        System.out.println("CreatureCard drag done.");
    }


    @Override
    protected void handleDrop(Dragboard dragboard, Object source) {
        if (source instanceof CreatureCard) {
            System.out.println("CreatureCard cannot be replaced by another CreatureCard.");
        }
        else if(source instanceof ProductCard){
            String imagePath = dragboard.getString();
            if (getParent() instanceof TilePane) {
                TilePane parentContainer = (TilePane) getParent();
                int index = parentContainer.getChildren().indexOf(this);
                onMakan.Notify(new Tuple<>(index, imagePath));
            }
        }
        else if (source instanceof ItemCard) {
            System.out.println("CreatureCard can be replaced by ItemCard or ProductCard.");
            // No change in image or class
        } else {
            System.out.println("CreatureCard cannot be replaced by this type.");
        }
    }
}
