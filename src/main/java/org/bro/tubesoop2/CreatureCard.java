package org.bro.tubesoop2;

import javafx.scene.input.Dragboard;
import java.util.Objects;

public class CreatureCard extends DraggableItem {

    public CreatureCard(String imagePath) {
        super(imagePath);
    }

    @Override
    public void dragDoneAction() {
        System.out.println("CreatureCard drag done.");
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

    @Override
    protected void handleDrop(Dragboard dragboard, Object source) {
        if (source instanceof CreatureCard) {
            System.out.println("CreatureCard cannot be replaced by another CreatureCard.");
        } else if (source instanceof ItemCard || source instanceof ProductCard) {
            System.out.println("CreatureCard can be replaced by ItemCard or ProductCard.");
            // No change in image or class
        } else {
            System.out.println("CreatureCard cannot be replaced by this type.");
        }
    }
}
