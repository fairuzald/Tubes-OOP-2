package org.bro.tubesoop2;

import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.resource.Resource;

import javafx.scene.input.Dragboard;

public class Card extends DraggableItem {

    Resource resource;
    
    public Resource getResource(){return resource;}

    public Card(Resource r, String path) {
        super(path);
        this.resource = r;
    }

    @Override
    public void dragDoneAction() {
        System.out.println("ProductCard drag done.");
    }

    @Override
    protected void handleDrop(Dragboard dragboard, Object source) {
        // Implement specific logic if needed
    }

    public static Card createCard(Resource r){
        if(r instanceof Product){
            return new ProductCard((Product) r);
        } else if(r instanceof Creature){
            return new CreatureCard((Creature) r);
        } else if(r instanceof Item){
            return new ItemCard((Item) r);
        }
        // assert false;
        return null;
    }
}
