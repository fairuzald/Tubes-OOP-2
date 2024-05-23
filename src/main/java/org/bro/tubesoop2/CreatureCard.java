package org.bro.tubesoop2;

import java.util.Objects;

public class CreatureCard extends DraggableItem{
    public CreatureCard(String imagePath) {
        super(imagePath);
    }

    public static CreatureCard getCreatureCard(String name) {
        String imagePath;
        if(Objects.equals(name, "BERUANG")){
            imagePath = "assets/Hewan/Bear.png";
        } else if (Objects.equals(name, "AYAM")){
            imagePath = "assets/Hewan/Chicken.png";
        } else if (Objects.equals(name, "SAPI")){
            imagePath = "assets/Hewan/Sheep.png";
        } else if (Objects.equals(name, "HIU DARAT")){
            imagePath = "assets/Hewan/Shark.png";
        } else if (Objects.equals(name, "KUDA")) {
            imagePath = "assets/Hewan/Horse.png";
        } else if (Objects.equals(name, "DOMBA")) {
            imagePath = "assets/Hewan/Sheep.png";
        } else if (Objects.equals(name, "BIJI JAGUNG")) {
            imagePath = "assets/Tanaman/Corn_Seeds.png";
        } else if (Objects.equals(name, "BIJI LABU")) {
            imagePath = "assets/Tanaman/Pumpkin_Seeds.png";
        } else if (Objects.equals(name, "BIJI STROBERI")) {
            imagePath = "assets/Tanaman/Strawberry_Seeds.png";
        } else {
            imagePath = "assets/basic.png";
        }

        return new CreatureCard(imagePath);
    }

    public void dragDoneAction(){
        System.out.println("masuk 2");
    }

}
