package org.bro.tubesoop2.item;

import org.bro.tubesoop2.animal.Animal;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.plant.Plant;

public class InstantHarvest extends Item {
    @Override
    public void consumedBy(Creature creature) {
        if (creature instanceof Animal) {
            Animal animal = (Animal) creature;
            System.out.println("Animal has consumed the InstantHarvest item.");
            // pop from gui
        } else if (creature instanceof Plant) {
            System.out.println("Creature has consumed the InstantHarvest item.");
            // pop from gui
        }
    }
}