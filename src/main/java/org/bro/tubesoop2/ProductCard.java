package org.bro.tubesoop2;

import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;

public class ProductCard extends DraggableItem {

    public ProductCard(String imagePath) {
        super(imagePath);
    }

    @Override
    public void dragDoneAction() {
        System.out.println("ProductCard drag done.");
    }

    @Override
    protected void handleDrop(Dragboard dragboard, Object source) {
        // Implement specific logic if needed
    }
}
