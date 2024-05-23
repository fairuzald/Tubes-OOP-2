package org.bro.tubesoop2;

public class ItemCard extends DraggableItem {
    public ItemCard(String imagePath) {
        super(imagePath);
    }

    @Override
    public void dragDoneAction() {
        System.out.println("Item drag done.");
    }
}
