package org.bro.tubesoop2;

import java.util.Objects;

public class CreatureCard extends DraggableItem{
    public CreatureCard(String imagePath) {
//        String imagePath;
//        if(Objects.equals(name, "Bear")){
//            imagePath = "assets/Hewan/Bear.png";
//        } else if (Objects.equals(name, "Chicken")){
//            imagePath = "assets/Hewan/Chicken.png";
//        } else if (Objects.equals(name, "Cow")){
//            imagePath = "assets/Hewan/Cow.png";
//        } else if (Objects.equals(name, "Hiu Darat")){
//            imagePath = "assets/Hewan/Hiu_Darat.png";
//        } else if (Objects.equals(name, "Horse")) {
//            imagePath = "assets/Hewan/Horse.png";
//        } else if (Objects.equals(name, "Shark")) {
//            imagePath = "assets/Hewan/Shark.png";
//        } else if (Objects.equals(name, "Sheep")) {
//            imagePath = "assets/Hewan/Sheep.png";
//        } else if (Objects.equals(name, "Corn Seeds")) {
//            imagePath = "assets/Tanaman/Corn_Seeds.png";
//        } else if (Objects.equals(name, "Pumpkin Seeds")) {
//            imagePath = "assets/Tanaman/Pumpkin_Seeds.png";
//        } else if (Objects.equals(name, "Strawberry Seeds")) {
//            imagePath = "assets/Tanaman/Strawberry_Seeds.png";
//        } else {
//            imagePath = "assets/basic.png";
//        }
        super(imagePath);
    }

    public void dragDoneAction(){
        System.out.println("masuk 2");

    }

}
