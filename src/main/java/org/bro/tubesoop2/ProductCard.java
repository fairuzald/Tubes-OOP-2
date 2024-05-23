package org.bro.tubesoop2;

public class ProductCard extends DraggableItem{
    public ProductCard(String imagePath) {
        super(imagePath);
    }

    public void dragDoneAction(){
        System.out.println("masuk");

    }

}
