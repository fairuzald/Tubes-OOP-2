package org.bro.tubesoop2;

import javafx.scene.input.Dragboard;
import javafx.scene.layout.TilePane;
import org.bro.tubesoop2.action.Action;
import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.resource.Resource;

import java.util.Objects;

public class CreatureCard extends DraggableItem {

    public CreatureCard(String imagePath) {
        super(imagePath);
    }

    public static Action<Tuple<Integer, String>> onMakan = new Action<>();

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


    public static String getNameFromImagePath(String imagePath) {
        String name;
        switch (imagePath) {
            case "assets/Hewan/Bear.png":
                name = "BERUANG";
                break;
            case "assets/Hewan/Chicken.png":
                name = "AYAM";
                break;
            case "assets/Hewan/Cow.png":
                name = "SAPI";
                break;
            case "assets/Hewan/Land_Shark.png":
                name = "HIU DARAT";
                break;
            case "assets/Hewan/Horse.png":
                name = "KUDA";
                break;
            case "assets/Hewan/Sheep.png":
                name = "DOMBA";
                break;
            case "assets/Tanaman/Corn_Seeds.png":
                name = "BIJI JAGUNG";
                break;
            case "assets/Tanaman/Pumpkin_Seeds.png":
                name = "BIJI LABU";
                break;
            case "assets/Tanaman/Strawberry_Seeds.png":
                name = "BIJI STROBERI";
                break;
            default:
                name = "UNKNOWN";
                break;
        }

        return name;
    }

    @Override
    protected void handleDrop(Dragboard dragboard, Object source) {
        if (source instanceof CreatureCard) {
            System.out.println("CreatureCard cannot be replaced by another CreatureCard.");
        }
        else if(source instanceof ProductCard){
            String imagePath = dragboard.getString();
            if (getParent() instanceof TilePane) {
                TilePane parentContainer = (TilePane) getParent();
                int index = parentContainer.getChildren().indexOf(this);
                onMakan.Notify(new Tuple<>(index, imagePath));
            }
        }
        else if (source instanceof ItemCard) {
            System.out.println("CreatureCard can be replaced by ItemCard or ProductCard.");
            // No change in image or class
        } else {
            System.out.println("CreatureCard cannot be replaced by this type.");
        }
    }
}
