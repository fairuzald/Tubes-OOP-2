package org.bro.tubesoop2;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class DraggableItem extends ImageView {

    public DraggableItem() {
        // Add event handlers for drag and drop
        this.setOnDragDetected(this::dragDetected);
        this.setOnDragDone(this::dragDone);
    }

    private void dragDetected(MouseEvent event) {
        ImageView sourceView = (ImageView) event.getSource();
        Dragboard dragboard = sourceView.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        content.putImage(sourceView.getImage());
        dragboard.setContent(content);

        event.consume();
    }

    private void dragDone(DragEvent event) {
        if (event.getTransferMode() == TransferMode.MOVE) {
            ImageView sourceView = (ImageView) event.getSource();
            sourceView.setImage(new Image("file:/assets/basic.png"));
        }
        event.consume();
    }
}
