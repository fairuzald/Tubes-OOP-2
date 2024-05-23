package org.bro.tubesoop2.item;

import org.bro.tubesoop2.animal.Animal;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.plant.Plant;

public class InstantHarvest implements Item {
    @Override
    public void consumedBy(Creature creature) {
        if (creature instanceof Animal) {
            Animal animal = (Animal) creature;
            // Implement logic specific to Animal
            System.out.println("Animal has consumed the Accelerate item.");
        } else if (creature instanceof Plant) {
            // Logic for other types of Creatures if necessary
            System.out.println("Creature has consumed the Accelerate item.");
        }
    }
}