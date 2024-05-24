package org.bro.tubesoop2;

import org.bro.tubesoop2.item.Item;

import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;

public class ItemCard extends Card {

    public ItemCard(Item item) {
        super(item, "assets/Item/"+item.getName()+".png");
    }

    @Override
    public void dragDoneAction() {
        System.out.println("ItemCard drag done.");
    }

    @Override
    protected void handleDrop(Dragboard dragboard, Object source) {
        // Implement specific logic if needed
    }
}
