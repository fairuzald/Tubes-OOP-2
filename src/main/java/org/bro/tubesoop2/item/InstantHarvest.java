package org.bro.tubesoop2.item;

import org.bro.tubesoop2.animal.Animal;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.plant.Plant;

public class InstantHarvest extends Item {
    @Override
    public void consumedBy(Creature creature) {
        if (creature instanceof Animal) {
            Animal animal = (Animal) creature;
            animal.addWeight(999);
            // pop from gui
        } else if (creature instanceof Plant) {
            Plant plant = (Plant) creature;
            plant.addAge(999);
            // pop from gui
        }
    }
}