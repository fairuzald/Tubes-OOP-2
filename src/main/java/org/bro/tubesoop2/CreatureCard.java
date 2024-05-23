package org.bro.tubesoop2;

import java.util.Objects;

public class CreatureCard extends DraggableItem{
    public CreatureCard(String imagePath) {
//
        super(imagePath);
    }

    public void dragDoneAction(){
        System.out.println("masuk 2");

    }

}
