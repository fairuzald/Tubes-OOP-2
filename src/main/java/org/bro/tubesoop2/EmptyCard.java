package org.bro.tubesoop2;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.TilePane;
import org.bro.tubesoop2.action.Action;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.grid.Location;
import org.bro.tubesoop2.resource.Resource;

public class EmptyCard extends DraggableItem {

    public EmptyCard() {
        super("assets/EMPTYCARD.png");
        this.isDefaultImage = true; // Mark this as the default image
    }

    public static Action<Tuple<Integer, Tuple<Resource, Integer>>> onDrop = new Action<>();

    @Override
    public void dragDoneAction() {
        System.out.println("Empty card - no action needed.");
    }

    @Override
    protected void handleDrop(Dragboard dragboard, Object source) {
        if (source instanceof CreatureCard) {
            System.out.println("EmptyCard can be replaced.");
            // Create a new instance of the appropriate card with the image from the
            // dragboard
            if (getParent() instanceof TilePane) {
                TilePane parentContainer = (TilePane) getParent();
                int index = parentContainer.getChildren().indexOf(this);

                int index2 = -1;
                if (parentContainer.getChildren().contains(source)) {
                    ObservableList<Node> s = parentContainer.getChildren();
                    index2 = s.indexOf(source);
                }

                if (((Node) source).getParent().getId().equals("ladangDeck")
                        && parentContainer.getId().equals("leftDeck")) {
                    // Display an error message dialog
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid action.");
                    alert.setContentText("You cannot drop from ladangDeck into leftDeck.");
                    alert.showAndWait();
                    index2 = -2;
                }

                CreatureCard cc = (CreatureCard) source;
                DraggableItem newCard = new CreatureCard((Creature) cc.getResource());
                parentContainer.getChildren().set(index, newCard);
                Tuple<Resource, Integer> resource = new Tuple<>(cc.getResource(), index);
                onDrop.Notify(new Tuple<>(index2, resource));
            }
        } else {
            System.out.println("EmptyCard cannot be replaced by " + source.getClass().getSimpleName());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("EmptyCard cannot be replaced by " + source.getClass().getSimpleName());
            alert.showAndWait();
            return;
        }
    }

    private String getImagePathFromDragboard(Dragboard dragboard) {
        // Extract the image path from the dragboard
        return dragboard.getString();
    }

}
