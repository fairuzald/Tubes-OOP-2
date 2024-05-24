package org.bro.tubesoop2.item;

import org.bro.tubesoop2.animal.Animal;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.plant.Plant;

public class Accelerate extends Item {
    @Override
    public void consumedBy(Creature creature) {
        super.consumedBy(creature);
        if (creature instanceof  Animal) {
            Animal animal = (Animal) creature;
            animal.addWeight((8));
        } else if (creature instanceof Plant) {
            Plant plant = (Plant) creature;
            plant.addAge(2);
        }
    }
}