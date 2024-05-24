package org.bro.tubesoop2;

import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;

public class ItemCard extends DraggableItem {

    public ItemCard(String imagePath) {
        super(imagePath);
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
