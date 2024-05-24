package org.bro.tubesoop2;

import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;

public class ProductCard extends DraggableItem {

    public ProductCard(String imagePath) {
        super(imagePath);
    }

    public static String getNameFromImagePath(String imagePath) {
        String name;
        switch (imagePath) {
            case "assets/Produk/corn.png":
                name = "JAGUNG";
                break;
            case "assets/Produk/daging_beruang.png":
                name = "DAGING BERUANG";
                break;
            case "assets/Hewan/daging_kuda.png":
                name = "DAGING KUDA";
                break;
            default:
                name = "UNKNOWN";
                break;
        }

        return name;
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
