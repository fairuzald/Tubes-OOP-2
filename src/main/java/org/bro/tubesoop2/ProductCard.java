package org.bro.tubesoop2;

import org.bro.tubesoop2.product.Product;

import javafx.scene.input.Dragboard;

public class ProductCard extends Card {

    public ProductCard(Product p) {
        super(p, "assets/Produk/" + p.getName()+".png");
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
