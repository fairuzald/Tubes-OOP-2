package org.bro.tubesoop2;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

public class DraggableItem extends ImageView {

    protected boolean isDefaultImage; // Change to protected to allow subclass access

    public DraggableItem() {
        Image testImage = new Image(getClass().getResourceAsStream("assets/test.png"));
        this.setImage(testImage);
        this.isDefaultImage = false;

        this.setOnDragDetected(this::dragDetected);
        this.setOnDragDone(this::dragDone);
        this.setOnDragOver(this::dragOver);
        this.setOnDragDropped(this::dragDropped)    ;
    }

    public void dragDetected(MouseEvent event) {
        ImageView sourceView = (ImageView) event.getSource();

        if (!isDefaultImage) {
            Dragboard dragboard = sourceView.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putImage(sourceView.getImage());
            dragboard.setContent(content);
        }

        event.consume();
    }

    public void dragDone(DragEvent event) {
        if (event.getTransferMode() == TransferMode.MOVE) {
            ImageView sourceView = (ImageView) event.getSource();
            Image defaultImage = new Image(getClass().getResourceAsStream("assets/basic.png"));
            sourceView.setImage(defaultImage);
            isDefaultImage = true;
            System.out.println("Image set to default.");
            if (this instanceof ProductCard) {
                ((ProductCard) this).tes();
            }
        }
        event.consume();
    }

    public void dragOver(DragEvent event) {
        if (event.getDragboard().hasImage() || event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    public void dragDropped(DragEvent event) {
        ImageView destinationView = (ImageView) event.getSource();
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasImage()) {
            destinationView.setImage(dragboard.getImage());
        }
        event.setDropCompleted(true);
        event.consume();
    }
}
