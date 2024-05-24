package org.bro.tubesoop2;

import javafx.scene.input.Dragboard;
import javafx.scene.layout.TilePane;

public class EmptyCard extends DraggableItem {
    public EmptyCard() {
        super("assets/basic.png");
        this.isDefaultImage = true;  // Mark this as the default image
    }

    @Override
    public void dragDoneAction() {
        System.out.println("Empty card - no action needed.");
    }

    @Override
    protected void handleDrop(Dragboard dragboard, Object source) {
        if (source instanceof CreatureCard || source instanceof ItemCard || source instanceof ProductCard) {
            System.out.println("EmptyCard can be replaced.");
            // Create a new instance of the appropriate card with the image from the dragboard
            String imagePath = getImagePathFromDragboard(dragboard);
            DraggableItem newCard;
            if (source instanceof CreatureCard) {
                newCard = new CreatureCard(imagePath);
            } else if (source instanceof ItemCard) {
                newCard = new ItemCard(imagePath);
            } else {
                newCard = new ProductCard(imagePath);
            }

            // Get the parent container and replace the EmptyCard with the new card
            if (getParent() instanceof TilePane) {
                TilePane parentContainer = (TilePane) getParent();
                int index = parentContainer.getChildren().indexOf(this);
                parentContainer.getChildren().set(index, newCard);
            }
        } else {
            System.out.println("EmptyCard cannot be replaced by this type.");
        }
    }

    private String getImagePathFromDragboard(Dragboard dragboard) {
        // Extract the image path from the dragboard
        return dragboard.getString();
    }
}
