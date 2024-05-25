package org.bro.tubesoop2;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class RedBorderController {

    private AnchorPane centerAnchorPane;
    private List<Rectangle> rectangles = new ArrayList<>();

    public RedBorderController(AnchorPane centerAnchorPane) {
        this.centerAnchorPane = centerAnchorPane;
    }

    @FXML
    public void initialize() {
        int numberOfColumns = 5;
        int numberOfRows = 4;
        double rectangleWidth = 95;
        double rectangleHeight = 142;
        double startX = 55;
        double startY = 32;
        double xIncrement = 114;
        double yIncrement = 157;

        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns; col++) {
                Rectangle rectangle = new Rectangle(rectangleWidth, rectangleHeight);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.RED);
                rectangle.setStrokeWidth(8.0);
                rectangle.setMouseTransparent(true);

                double x = startX + col * xIncrement;
                double y = startY + row * yIncrement;
                rectangle.setLayoutX(x);
                rectangle.setLayoutY(y);

                rectangles.add(rectangle);
                rectangle.setVisible(false);
                centerAnchorPane.getChildren().add(rectangle);

            }
        }
    }

    public void setRedBordersVisible(boolean visible, List<Integer> indices) {
        for (int index : indices) {
            if (index >= 0 && index < rectangles.size()) {
                rectangles.get(index).setVisible(visible);
            }
        }
    }
}

