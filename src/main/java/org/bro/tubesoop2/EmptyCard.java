package org.bro.tubesoop2;

import javafx.scene.input.Dragboard;
import javafx.scene.layout.TilePane;
import org.bro.tubesoop2.action.Action;
import org.bro.tubesoop2.grid.Location;
import org.bro.tubesoop2.resource.Resource;

public class EmptyCard extends DraggableItem {

    public EmptyCard() {
        super("assets/basic.png");
        this.isDefaultImage = true;  // Mark this as the default image
    }

    public static Action<Tuple<Resource, Integer>> onDrop = new Action<>();

    @Override
    public void dragDoneAction() {
        System.out.println("Empty card - no action needed.");
    }

    @Override
    protected void handleDrop(Dragboard dragboard, Object source) {
        if (source instanceof CreatureCard) {
            System.out.println("EmptyCard can be replaced.");
            // Create a new instance of the appropriate card with the image from the dragboard
            String imagePath = getImagePathFromDragboard(dragboard);
                if (getParent() instanceof TilePane) {
                    TilePane parentContainer = (TilePane) getParent();
                    int index = parentContainer.getChildren().indexOf(this);
                    System.out.println(index);
                    Resource rsc = new Resource(CreatureCard.getNameFromImagePath(imagePath));
                    DraggableItem newCard = new CreatureCard(imagePath);
                    System.out.println(rsc.getName());
                    parentContainer.getChildren().set(index, newCard);
                    onDrop.Notify(new Tuple<>(rsc, index));}
        } else {
            System.out.println("EmptyCard cannot be replaced by this type.");
        }
    }

    private String getImagePathFromDragboard(Dragboard dragboard) {
        // Extract the image path from the dragboard
        return dragboard.getString();
    }

}
