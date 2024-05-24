package org.bro.tubesoop2;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.TilePane;

public abstract class DraggableItem extends ImageView {
    private String imagePath;
    private boolean isDragable = true;
    protected boolean isDefaultImage;

    public DraggableItem(String imagePath) {
        this.isDefaultImage = false;
        Image testImage = new Image(getClass().getResourceAsStream(imagePath));
        this.setImage(testImage);
        this.imagePath = imagePath;

        this.setOnDragDetected(this::dragDetected);
        this.setOnDragDone(this::dragDone);
        this.setOnDragOver(this::dragOver);
        this.setOnDragDropped(this::dragDropped);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void dragDetected(MouseEvent event) {
        if(isDragable) {
            ImageView sourceView = (ImageView) event.getSource();

            if (!isDefaultImage) {
                Dragboard dragboard = sourceView.startDragAndDrop(TransferMode.MOVE);

                ClipboardContent content = new ClipboardContent();
                content.putImage(sourceView.getImage());
                content.putString(imagePath); // Set the image path on the dragboard
                dragboard.setContent(content);
            }

            event.consume();
        }
    }

    public void dragDone(DragEvent event) {
        if (event.getTransferMode() == TransferMode.MOVE) {
            dragDoneAction();
            replaceWithEmptyCard();
        }
        event.consume();
    }

    private void replaceWithEmptyCard() {
        if (getParent() instanceof TilePane) {
            TilePane parentContainer = (TilePane) getParent();
            int index = parentContainer.getChildren().indexOf(this);
            EmptyCard emptyCard = new EmptyCard();
            parentContainer.getChildren().set(index, emptyCard);
        }
    }

    public void dragOver(DragEvent event) {
        if (event.getGestureSource() != this && event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    public void dragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (!dragboard.hasImage())  {
            event.setDropCompleted(true);
            event.consume();
            return;
        }

        Object source = event.getGestureSource();
        if (isDragable) {
            if (isValidReplacement(source)) {
                handleDrop(dragboard, source);
                event.setDropCompleted(true);
            } else {
                System.out.println("Invalid drop action.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid drop action.");
                alert.showAndWait();
                event.setDropCompleted(false);
            }

        } else {
            System.out.println("drop to enemy!");
            dropEnemy(dragboard, source);
        }

    }

    protected abstract void handleDrop(Dragboard dragboard, Object source);

    protected void dropEnemy(Dragboard dragboard, Object source){

    }

    protected boolean isValidReplacement(Object source) {
        if (this instanceof CreatureCard && source instanceof CreatureCard) {
            return false;
        }
        if (source instanceof EmptyCard) {
            return false;
        }
        if (this instanceof EmptyCard && !(source instanceof CreatureCard)) {
            return false;
        }
        return true;
    }

    public void setDragState(boolean isDragable) {
        this.isDragable = isDragable;
    }

    public boolean isDragable() {
        return isDragable;
    }

    abstract public void dragDoneAction();
}
